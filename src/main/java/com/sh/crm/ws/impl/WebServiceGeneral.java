package com.sh.crm.ws.impl;

import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;
import com.sh.ws.userServices.retailservices.baj.com.PagingInfo;
import com.sh.ws.userServices.retailservices.baj.com.SortOrder;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.GregorianCalendar;

public abstract class WebServiceGeneral {

    public WSResponseHolder<?> generateFailureResponse() {
        WSResponseHolder<?> wsResponseHolder;
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setState(ResponseState.FAILURE);
        responseHeader.setStatus(new BigInteger("-1"));
        responseHeader.setMessageID("UNKNOW ERROR");
        return new WSResponseHolder<>(-1, null, responseHeader);
    }

    public PagingInfo getDefaultPagingInfo() {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageNo(BigInteger.ZERO);
        pagingInfo.setPageSize(BigInteger.ZERO);
        pagingInfo.setSortOrder(SortOrder.ASCENDING);
        pagingInfo.setOrderBy("");
        pagingInfo.setTotalRecords(BigInteger.valueOf(1000));
        return pagingInfo;
    }

    public XMLGregorianCalendar getXmlGregorianCalendar(long value) {
        try {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(value);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
