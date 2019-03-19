package com.sh.crm.ws.handlers;

import com.sh.crm.jpa.config.BeanUtil;
import com.sh.crm.jpa.entities.MWErrorLogs;
import com.sh.crm.jpa.entities.MWLogs;
import com.sh.crm.jpa.repos.ws.MWErrorLogsRepo;
import com.sh.crm.jpa.repos.ws.MWLogsRepo;
import com.sh.general.utils.MyUtils;
import com.sh.ws.pojo.HeaderLoggerPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Calendar;
import java.util.Set;


public abstract class BasicHandler implements javax.xml.ws.handler.soap.SOAPHandler<SOAPMessageContext> {

    protected String WSDLFile;
    protected Logger log = LoggerFactory.getLogger( this.getClass() );
    //protected MWErrorLogs errorLog = new MWErrorLogs();
    protected String messageSent;
    protected DocumentBuilderFactory dbFactory;

    //protected BenefeciaryLoggingRecord loggingRecord = new BenefeciaryLoggingRecord();
    protected DocumentBuilder dBuilder;
    protected long responseTime;
    protected Long mwLogId;
    protected MWErrorLogs mwErrorLogs;
    private MWLogsRepo mwLogsRepo;
    private MWErrorLogsRepo mwErrorLogsRepo;

    public BasicHandler() {
        mwErrorLogsRepo = BeanUtil.getBean( MWErrorLogsRepo.class );
        mwLogsRepo = BeanUtil.getBean( MWLogsRepo.class );
    }

    protected abstract void setWSDLFile();

    private void init() {
        setWSDLFile();
        getIPfromHost();
    }

    public void logMessage(SOAPMessageContext context) {
        init();
        MWLogs mwLogs = new MWLogs();
        try {
            // loggingRecord.setWsdlFile(WSDLFile);

            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            Boolean output = (Boolean) context.get( MessageContext.MESSAGE_OUTBOUND_PROPERTY );

            SOAPMessage soapMsg = context.getMessage();

            ByteArrayOutputStream result = new ByteArrayOutputStream();

            soapMsg.writeTo( result );

            if (output) {
                mwLogs = new MWLogs();
                mwErrorLogs = new MWErrorLogs();
                mwLogs.setWsdlFile( getWSDLFile() );
                responseTime = System.currentTimeMillis();
                messageSent = result.toString();
                messageSent = MyUtils.maskMWHeader( messageSent );
                //loggingRecord.setFullRequest(messageSent);
                mwLogs.setFullRequest( messageSent );
                try {
                    Document doc = dBuilder.parse( new InputSource( new StringReader( messageSent ) ) );
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName( "ns1:requestHeader" );
                    if (nList == null || nList.getLength() <= 0) {
                        nList = doc.getElementsByTagName( "ns2:requestHeader" );
                    }
                    HeaderLoggerPojo header = parseHeaderNodeList( nList );
                    NodeList nList2 = doc.getElementsByTagName( "soapenv:Body" );

                    if (nList2 == null || nList2.getLength() <= 0) {
                        nList2 = doc.getElementsByTagName( "S:Body" );
                    }
                    String serviceName = parseBodyNode( nList2 );
                    header.setReqServiceName( serviceName );
                    // loggingRecord.setHeader(header);
                    mwLogs.setCuid( header.getCuid() );
                    mwLogs.setCustomerNumber( header.getCustomerId() );
                    mwLogs.setReqServiceName( serviceName );
                    mwLogs.setCustomerOfficialID( header.getIdNumber() );
                    mwLogs.setDateTime( Calendar.getInstance().getTime() );
                    mwLogs.setRequestUUID( header.getRequestUUID() );
                    mwLogs.setServerIP( MyUtils.getIP() );
                    mwLogs.setTokenKey( header.getTokenKey() );
                    mwErrorLogs.setBasicNumber( header.getCustomerId() );
                    mwErrorLogs.setDateTime( Calendar.getInstance().getTime() );
                    mwErrorLogs.setIDNumber( header.getIdNumber() );
                    mwErrorLogs.setRquid( header.getRequestUUID() );
                    mwErrorLogs.setServiceName( header.getReqServiceName() );
                    mwErrorLogs.setServer( MyUtils.getIP() );
                    mwErrorLogs.setWsdl( getWSDLFile() );
                    mwLogs = mwLogsRepo.save( mwLogs );
                    mwLogId = mwLogs.getId();
                    mwLogs = new MWLogs();
                    //  mwErrorLogsRepo.save(mwErrorLogs);
                } catch (Exception e) {
                    log.error( e.toString() );
                    e.printStackTrace();
                }
                log.info( "Message sent: " + messageSent );

            } else {
                responseTime = System.currentTimeMillis() - responseTime;
                log.info( "Processing time for message: " + mwLogId + " is: "
                        + responseTime );
                log.info( "Message received: " + result.toString() );
                Document doc = dBuilder.parse( new InputSource( new StringReader( result.toString() ) ) );
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName( "NS1:Body" );
                NodeList nListHeader = doc.getElementsByTagName( "NS2:responseHeader" );
                if (nListHeader == null || nListHeader.getLength() <= 0) {
                    nListHeader = doc.getElementsByTagName( "NS1:responseHeader" );
                }
                String serviceName = parseBodyNode( nList );
                mwErrorLogs = parseHeaderNodeList( nListHeader, mwErrorLogs );

                if (mwLogId != null)
                    mwLogsRepo.updateMWLogs( serviceName, result.toString(), mwLogId );
                try {
                    if (!mwErrorLogs.getNativeStatus().equals( "0" ))
                        mwErrorLogsRepo.save( mwErrorLogs );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mwErrorLogs = new MWErrorLogs();
                mwLogs = new MWLogs();
                messageSent = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error( e.toString() );
        }
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        logMessage( context );

        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        // System.out.println("Client : handleFault()......");
        try {

            SOAPMessage soapMsg = context.getMessage();

            ByteArrayOutputStream result = new ByteArrayOutputStream();

            soapMsg.writeTo( result );

            log.error( result.toString() );

        } catch (Exception e) {
            log.error( e.toString() );
        }
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }

    @Override
    public Set<QName> getHeaders() {
        // System.out.println("Client : getHeaders()......");
        return null;
    }

    protected String parseBodyNode(NodeList nodeList) {

        String serviceName = "";

        try {
            if (nodeList != null && nodeList.getLength() > 0) {
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Element element = (Element) nodeList.item( j );
                    NodeList childs = element.getChildNodes();
                    for (int i = 0; i < childs.getLength(); i++) {
                        Node n = (Node) childs.item( i );
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element childElement = (Element) n;
                            String attributeName = childElement.getNodeName();
                            if (attributeName == null || attributeName.equals( "" ))
                                continue;

                            serviceName = attributeName;
                            // System.out.println("Service: " + attributeName);

                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        return serviceName;

    }

    protected HeaderLoggerPojo parseHeaderNodeList(NodeList nodeList) {

        HeaderLoggerPojo header = new HeaderLoggerPojo();

        if (nodeList != null && nodeList.getLength() > 0) {
            for (int j = 0; j < nodeList.getLength(); j++) {
                Element element = (Element) nodeList.item( j );
                NodeList childs = element.getChildNodes();
                for (int i = 0; i < childs.getLength(); i++) {
                    Node n = (Node) childs.item( i );
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) n;
                        String attributeName = childElement.getNodeName();
                        if (attributeName == null || attributeName.equals( "" ))
                            continue;

                        if (attributeName.equalsIgnoreCase( "language" )) {
                            // System.out.println(attributeName);
                            header.setLanguage( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "requestID" )) {
                            header.setRequestUUID( childElement.getTextContent() );

                        } else if (attributeName.equalsIgnoreCase( "userID" )) {
                            header.setUserID( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "customerNo" )) {
                            header.setCustomerId( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "channelID" )) {
                            header.setChannel( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "tokenKey" )) {
                            header.setTokenKey( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "cuid" )) {
                            header.setCuid( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "idNumber" )) {
                            header.setIdNumber( childElement.getTextContent() );
                        }

                    }

                }
            }
        }
        return header;

    }

    protected MWErrorLogs parseHeaderNodeList(NodeList nodeList, MWErrorLogs record) {

        if (nodeList != null && nodeList.getLength() > 0) {
            for (int j = 0; j < nodeList.getLength(); j++) {
                Element element = (Element) nodeList.item( j );
                NodeList childs = element.getChildNodes();
                for (int i = 0; i < childs.getLength(); i++) {
                    Node n = (Node) childs.item( i );
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) n;
                        String attributeName = childElement.getNodeName();
                        if (attributeName == null || attributeName.equals( "" ))
                            continue;

                        if (attributeName.equalsIgnoreCase( "state" )) {
                            // System.out.println(attributeName);
                            record.setNativeState( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "status" )) {
                            record.setNativeStatus( childElement.getTextContent() );

                        } else if (attributeName.equalsIgnoreCase( "nativeError" )) {
                            record.setNativeErrorExt( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "description" )) {
                            record.setNativeErrorDec( childElement.getTextContent() );
                        } else if (attributeName.equalsIgnoreCase( "messageID" )) {
                            record.setMessageID( childElement.getTextContent() );
                        }

                    }

                }
            }
        }
        nodeList = null;
        return record;

    }

    private void getIPfromHost() {
        try {
            log.debug( "Getting host name of WSDL file {}", getWSDLFile() );
            URL url = new URL( getWSDLFile() );
            InetAddress address = InetAddress.getByName( url.getHost() );
            String ip = address.getHostAddress();
            String host = url.getHost();
            this.WSDLFile.replace( host, ip );
        } catch (Exception e) {
            log.error( "Error Getting IP address: " + e );
            e.printStackTrace();
        }

    }

    public String getWSDLFile() {
        return this.WSDLFile;
    }
}
