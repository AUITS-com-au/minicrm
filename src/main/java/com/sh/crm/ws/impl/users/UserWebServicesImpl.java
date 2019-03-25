package com.sh.crm.ws.impl.users;

import com.sh.crm.security.util.Utils;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.crm.ws.proxy.users.UsersServicesProxy;
import com.sh.ws.request.RequestHeader;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;
import com.sh.ws.userServices.userservices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import javax.xml.ws.Holder;
import java.math.BigInteger;

@Service
public class UserWebServicesImpl {
    @Autowired
    private UsersServicesProxy usersServicesProxy;

    public WSResponseHolder getCustomerProfileByInput(String inputValue,int inputType, String lang) {
        String customerBasic = "";
        switch (inputType){
            case 1:{customerBasic = inputValue;
            break;}
            case 2:{
                WSResponseHolder<GetCustomerNoFromIdResponse> holder = getCustomerNoFromId(inputValue,lang);
                if(holder != null && !holder.getValue().getCustomerList().getCustomerName().isEmpty()) {
                    customerBasic = holder.getValue().getCustomerList().getCustomerName().get(0).getCustomerNo();
                }
                break;
            }
            case 3:{
                WSResponseHolder<GetCustomerNoFromMobileResponse> holder = getCustomerNoFromMobile(inputValue,lang);
                if(holder != null && !holder.getValue().getCustomerList().getCustomerName().isEmpty()) {
                    customerBasic = holder.getValue().getCustomerList().getCustomerName().get(0).getCustomerNo();
                }
                break;
            }
        }

        GetCustomerProfileRequest request = new GetCustomerProfileRequest();
        WSResponseHolder<GetCustomerProfileResponse> wsResponseHolder;
        request.setCustomerNo( customerBasic );
        request.setExtendData( true );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerProfileResponse> customerProfileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerProfile( request, requestHeader, customerProfileResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, customerProfileResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }

        return wsResponseHolder;

    }


    public WSResponseHolder getCustomerProfile(String customerBasic, String lang) {
        GetCustomerProfileRequest request = new GetCustomerProfileRequest();
        WSResponseHolder<GetCustomerProfileResponse> wsResponseHolder;
        request.setCustomerNo( customerBasic );
        request.setExtendData( true );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerProfileResponse> customerProfileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerProfile( request, requestHeader, customerProfileResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, customerProfileResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }

        return wsResponseHolder;

    }

    public WSResponseHolder getCustomerIncomeSource(String customerBasic, String lang) {
        GetCustomerIncomeSourceRequest request = new GetCustomerIncomeSourceRequest();
        WSResponseHolder<GetCustomerIncomeSourceResponse> wsResponseHolder;
        request.setCustomerNo( customerBasic );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerIncomeSourceResponse> customerIncomeSourceResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerIncomeSource( request, requestHeader, customerIncomeSourceResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, customerIncomeSourceResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerNoFromId(String nationalID, String lang) {
        GetCustomerNoFromIdRequest request = new GetCustomerNoFromIdRequest();
        WSResponseHolder<GetCustomerNoFromIdResponse> wsResponseHolder;
        nationalID = Utils.getFormattedID(nationalID);
        request.setOfficialId( nationalID );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( null, nationalID, lang );
        Holder<GetCustomerNoFromIdResponse> customerNoFromIdResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerNoFromId( request, requestHeader, customerNoFromIdResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, customerNoFromIdResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }
        return wsResponseHolder;
    }


    public WSResponseHolder getCustomerNoFromMobile(String mobileNo, String lang) {
        GetCustomerNoFromMobileRequest request = new GetCustomerNoFromMobileRequest();
        WSResponseHolder<GetCustomerNoFromMobileResponse> wsResponseHolder;
        mobileNo = Utils.setPrepaidMobileScheme(mobileNo);
        request.setMobileNo(mobileNo);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( null, null, lang );
        Holder<GetCustomerNoFromMobileResponse> customerNoFromMobileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerNoFromMobile( request, requestHeader, customerNoFromMobileResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, customerNoFromMobileResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }
        return wsResponseHolder;
    }



    public WSResponseHolder getCustomerPersona(String customerBasic, String lang) {
        GetCustomerPersonaRequest request = new GetCustomerPersonaRequest();
        WSResponseHolder<GetCustomerPersonaResponse> wsResponseHolder;
        request.setCustomerNo(customerBasic);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerPersonaResponse> customerPersonaResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerPersona( request, requestHeader, customerPersonaResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, customerPersonaResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerRM(String customerBasic, String lang) {
        GetCustomerRMRequest request = new GetCustomerRMRequest();
        WSResponseHolder<GetCustomerRMResponse> wsResponseHolder;
        request.setCustomerNo(customerBasic);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerRMResponse> customerRMResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerRM( request, requestHeader, customerRMResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, customerRMResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }
        return wsResponseHolder;
    }


    public WSResponseHolder getEmployeeDetail(String userID, String lang) {
        GetEmployeeDetailRequest request = new GetEmployeeDetailRequest();
        WSResponseHolder<GetEmployeeDetailResponse> wsResponseHolder;
        request.setUserId(userID);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( null, null, lang );
        Holder<GetEmployeeDetailResponse> employeeDetailResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getEmployeeDetail( request, requestHeader, employeeDetailResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, employeeDetailResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getSpouseDetails(String customerBasic, String lang) {
        GetSpouseDetailsRequest request = new GetSpouseDetailsRequest();
        WSResponseHolder<GetSpouseDetailsResponse> wsResponseHolder;
        request.setCustomerNo(customerBasic);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetSpouseDetailsResponse> spouseDetailsResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getSpouseDetails( request, requestHeader, spouseDetailsResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, spouseDetailsResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }
        return wsResponseHolder;
    }



}
