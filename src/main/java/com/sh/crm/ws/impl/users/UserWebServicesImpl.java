package com.sh.crm.ws.impl.users;

import com.sh.crm.security.util.Utils;
import com.sh.crm.ws.impl.WebServiceGeneral;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.crm.ws.proxy.users.UsersServicesProxy;
import com.sh.ws.request.RequestHeader;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.userServices.userservices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Holder;

@Service
public class UserWebServicesImpl extends WebServiceGeneral {
    @Autowired
    private UsersServicesProxy usersServicesProxy;

    public WSResponseHolder getCustomerProfileByInput(String inputValue, int inputType, String lang) {
        String customerBasic = "";
        switch (inputType) {
            case 1: {
                customerBasic = inputValue;
                break;
            }
            case 2: {
                WSResponseHolder<GetCustomerNoFromIdResponse> holder = getCustomerNoFromId( inputValue, lang );
                if (holder != null && !holder.getValue().getCustomerList().getCustomerName().isEmpty()) {
                    customerBasic = holder.getValue().getCustomerList().getCustomerName().get( 0 ).getCustomerNo();
                }
                break;
            }
            case 3: {
                WSResponseHolder<GetCustomerNoFromMobileResponse> holder = getCustomerNoFromMobile( inputValue, lang );
                if (holder != null && !holder.getValue().getCustomerList().getCustomerName().isEmpty()) {
                    customerBasic = holder.getValue().getCustomerList().getCustomerName().get( 0 ).getCustomerNo();
                }
                break;
            }
        }

        return getCustomerProfile( customerBasic, lang );

    }


    public WSResponseHolder getCustomerProfile(String customerBasic, String lang) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetCustomerProfileRequest request = new GetCustomerProfileRequest();
        request.setCustomerNo( customerBasic );
        request.setExtendData( true );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerProfileResponse> customerProfileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerProfile( request, requestHeader, customerProfileResponseHolder, responseHeaderHolder );
            wsResponseHolder = usersServicesProxy.handleResponseBody( responseHeaderHolder, customerProfileResponseHolder );
            if (customerProfileResponseHolder == null || customerProfileResponseHolder.value == null)
                throw new Exception( "MW Response Message is Empty" );
            customerProfileResponseHolder.value.getCaa().setCustomerNo( customerBasic );

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerIncomeSource(String customerBasic, String lang) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetCustomerIncomeSourceRequest request = new GetCustomerIncomeSourceRequest();
        request.setCustomerNo( customerBasic );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerIncomeSourceResponse> customerIncomeSourceResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerIncomeSource( request, requestHeader, customerIncomeSourceResponseHolder, responseHeaderHolder );
            wsResponseHolder = usersServicesProxy.handleResponseBody( responseHeaderHolder, customerIncomeSourceResponseHolder );

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerNoFromId(String nationalID, String lang) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetCustomerNoFromIdRequest request = new GetCustomerNoFromIdRequest();
        nationalID = Utils.getFormattedID( nationalID );
        request.setOfficialId( nationalID );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( null, nationalID, lang );
        Holder<GetCustomerNoFromIdResponse> customerNoFromIdResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerNoFromId( request, requestHeader, customerNoFromIdResponseHolder, responseHeaderHolder );
            wsResponseHolder = usersServicesProxy.handleResponseBody( responseHeaderHolder, customerNoFromIdResponseHolder );

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerNoFromMobile(String mobileNo, String lang) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetCustomerNoFromMobileRequest request = new GetCustomerNoFromMobileRequest();
        mobileNo = Utils.setPrepaidMobileScheme( mobileNo );
        request.setMobileNo( mobileNo );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( null, null, lang );
        Holder<GetCustomerNoFromMobileResponse> customerNoFromMobileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerNoFromMobile( request, requestHeader, customerNoFromMobileResponseHolder, responseHeaderHolder );
            wsResponseHolder = usersServicesProxy.handleResponseBody( responseHeaderHolder, customerNoFromMobileResponseHolder );
        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerPersona(String customerBasic, String lang) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetCustomerPersonaRequest request = new GetCustomerPersonaRequest();
        request.setCustomerNo( customerBasic );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerPersonaResponse> customerPersonaResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerPersona( request, requestHeader, customerPersonaResponseHolder, responseHeaderHolder );
            wsResponseHolder = usersServicesProxy.handleResponseBody( responseHeaderHolder, customerPersonaResponseHolder );
        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerRM(String customerBasic, String lang) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetCustomerRMRequest request = new GetCustomerRMRequest();

        request.setCustomerNo( customerBasic );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerRMResponse> customerRMResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerRM( request, requestHeader, customerRMResponseHolder, responseHeaderHolder );
            wsResponseHolder = usersServicesProxy.handleResponseBody( responseHeaderHolder, customerRMResponseHolder );

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }


    public WSResponseHolder getEmployeeDetail(String userID, String lang) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetEmployeeDetailRequest request = new GetEmployeeDetailRequest();

        request.setUserId( userID );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( null, null, lang );
        Holder<GetEmployeeDetailResponse> employeeDetailResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getEmployeeDetail( request, requestHeader, employeeDetailResponseHolder, responseHeaderHolder );
            wsResponseHolder = usersServicesProxy.handleResponseBody( responseHeaderHolder, employeeDetailResponseHolder );

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getSpouseDetails(String customerBasic, String lang) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetSpouseDetailsRequest request = new GetSpouseDetailsRequest();
        request.setCustomerNo( customerBasic );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetSpouseDetailsResponse> spouseDetailsResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getSpouseDetails( request, requestHeader, spouseDetailsResponseHolder, responseHeaderHolder );
            wsResponseHolder = usersServicesProxy.handleResponseBody( responseHeaderHolder, spouseDetailsResponseHolder );

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }


}
