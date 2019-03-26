package com.sh.crm.ws.impl.users;

import com.sh.crm.security.util.Utils;
import com.sh.crm.ws.impl.WebServiceGeneral;
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
                WSResponseHolder<GetCustomerNoFromIdResponse> holder = getCustomerNoFromId(inputValue, lang);
                if (holder != null && !holder.getValue().getCustomerList().getCustomerName().isEmpty()) {
                    customerBasic = holder.getValue().getCustomerList().getCustomerName().get(0).getCustomerNo();
                }
                break;
            }
            case 3: {
                WSResponseHolder<GetCustomerNoFromMobileResponse> holder = getCustomerNoFromMobile(inputValue, lang);
                if (holder != null && !holder.getValue().getCustomerList().getCustomerName().isEmpty()) {
                    customerBasic = holder.getValue().getCustomerList().getCustomerName().get(0).getCustomerNo();
                }
                break;
            }
        }

        GetCustomerProfileRequest request = new GetCustomerProfileRequest();

        request.setCustomerNo(customerBasic);
        request.setExtendData(true);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(customerBasic, null, lang);
        Holder<GetCustomerProfileResponse> customerProfileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerProfile(request, requestHeader, customerProfileResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<GetCustomerProfileResponse>(status, customerProfileResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }

        return wsResponseHolder;

    }


    public WSResponseHolder getCustomerProfile(String customerBasic, String lang) {
        GetCustomerProfileRequest request = new GetCustomerProfileRequest();
        request.setCustomerNo(customerBasic);
        request.setExtendData(true);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(customerBasic, null, lang);
        Holder<GetCustomerProfileResponse> customerProfileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerProfile(request, requestHeader, customerProfileResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);
            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<GetCustomerProfileResponse>(status, customerProfileResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }

        return wsResponseHolder;

    }

    public WSResponseHolder getCustomerIncomeSource(String customerBasic, String lang) {
        GetCustomerIncomeSourceRequest request = new GetCustomerIncomeSourceRequest();
        request.setCustomerNo(customerBasic);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(customerBasic, null, lang);
        Holder<GetCustomerIncomeSourceResponse> customerIncomeSourceResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerIncomeSource(request, requestHeader, customerIncomeSourceResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>(status, customerIncomeSourceResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }

        } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerNoFromId(String nationalID, String lang) {
        GetCustomerNoFromIdRequest request = new GetCustomerNoFromIdRequest();
        nationalID = Utils.getFormattedID(nationalID);
        request.setOfficialId(nationalID);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(null, nationalID, lang);
        Holder<GetCustomerNoFromIdResponse> customerNoFromIdResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerNoFromId(request, requestHeader, customerNoFromIdResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>(status, customerNoFromIdResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }

        } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }
        return wsResponseHolder;
    }


    public WSResponseHolder getCustomerNoFromMobile(String mobileNo, String lang) {
        GetCustomerNoFromMobileRequest request = new GetCustomerNoFromMobileRequest();
        mobileNo = Utils.setPrepaidMobileScheme(mobileNo);
        request.setMobileNo(mobileNo);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(null, null, lang);
        Holder<GetCustomerNoFromMobileResponse> customerNoFromMobileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerNoFromMobile(request, requestHeader, customerNoFromMobileResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>(status, customerNoFromMobileResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }
         } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }
        return wsResponseHolder;
    }


    public WSResponseHolder getCustomerPersona(String customerBasic, String lang) {
        GetCustomerPersonaRequest request = new GetCustomerPersonaRequest();

        request.setCustomerNo(customerBasic);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(customerBasic, null, lang);
        Holder<GetCustomerPersonaResponse> customerPersonaResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerPersona(request, requestHeader, customerPersonaResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>(status, customerPersonaResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }

        } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getCustomerRM(String customerBasic, String lang) {
        GetCustomerRMRequest request = new GetCustomerRMRequest();

        request.setCustomerNo(customerBasic);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(customerBasic, null, lang);
        Holder<GetCustomerRMResponse> customerRMResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerRM(request, requestHeader, customerRMResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>(status, customerRMResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }

        } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }
        return wsResponseHolder;
    }


    public WSResponseHolder getEmployeeDetail(String userID, String lang) {
        GetEmployeeDetailRequest request = new GetEmployeeDetailRequest();

        request.setUserId(userID);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(null, null, lang);
        Holder<GetEmployeeDetailResponse> employeeDetailResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getEmployeeDetail(request, requestHeader, employeeDetailResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>(status, employeeDetailResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }

        } catch (Exception e) {
            generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder getSpouseDetails(String customerBasic, String lang) {
        GetSpouseDetailsRequest request = new GetSpouseDetailsRequest();
        request.setCustomerNo(customerBasic);
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader(customerBasic, null, lang);
        Holder<GetSpouseDetailsResponse> spouseDetailsResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getSpouseDetails(request, requestHeader, spouseDetailsResponseHolder, responseHeaderHolder);
            int status = usersServicesProxy.handleResponse(responseHeaderHolder);

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>(status, spouseDetailsResponseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }

        } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }
        return wsResponseHolder;
    }


}
