package com.sh.crm.rest.ws.usersWebServices;

import com.sh.crm.rest.ws.GeneralWSRest;
import com.sh.crm.ws.impl.users.UserWebServicesImpl;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.userServices.userservices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ws/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserWebServicesRest extends GeneralWSRest {
    @Autowired
    private UserWebServicesImpl userWebServices;

    @GetMapping("getCustomerProfileByInput/{inputValue}/{inputType}/{lang}")
    ResponseEntity<?> getCustomerProfileByInput(@PathVariable("inputValue") String inputValue, @PathVariable("inputType") int inputType, @PathVariable("lang") String lang) {
        WSResponseHolder<GetCustomerProfileResponse> wsResponseHolder = userWebServices.getCustomerProfileByInput(inputValue, inputType, lang);
        return handleResponse(wsResponseHolder);
    }


    @GetMapping("customerProfile/{customerBasic}/{lang}")
    ResponseEntity<?> getCustomerProfile(@PathVariable("customerBasic") String customerBasic, @PathVariable("lang") String lang) {
        WSResponseHolder<GetCustomerProfileResponse> wsResponseHolder = userWebServices.getCustomerProfile(customerBasic, lang);
        return handleResponse(wsResponseHolder);
    }


    @GetMapping("customerIncome/{customerBasic}/{lang}")
    ResponseEntity<?> getCustomerIncome(@PathVariable("customerBasic") String customerBasic, @PathVariable("lang") String lang) {
        WSResponseHolder<GetCustomerIncomeSourceResponse> wsResponseHolder = userWebServices.getCustomerIncomeSource(customerBasic, lang);
        return handleResponse(wsResponseHolder);
    }


    @GetMapping("customerNoFromID/{nationalID}/{lang}")
    ResponseEntity<?> getCustomerNoFromId(@PathVariable("nationalID") String nationalID, @PathVariable("lang") String lang) {
        WSResponseHolder<GetCustomerNoFromIdResponse> wsResponseHolder = userWebServices.getCustomerNoFromId(nationalID, lang);
        return handleResponse(wsResponseHolder);
    }

    @GetMapping("customerNoFromMobile/{mobileNo}/{lang}")
    ResponseEntity<?> getCustomerNoFromMobile(@PathVariable("mobileNo") String mobileNo, @PathVariable("lang") String lang) {
        WSResponseHolder<GetCustomerNoFromMobileResponse> wsResponseHolder = userWebServices.getCustomerNoFromMobile(mobileNo, lang);
        return handleResponse(wsResponseHolder);
    }


    @GetMapping("customerPersona/{customerBasic}/{lang}")
    ResponseEntity<?> getCustomerPersona(@PathVariable("customerBasic") String customerBasic, @PathVariable("lang") String lang) {
        WSResponseHolder<GetCustomerPersonaResponse> wsResponseHolder = userWebServices.getCustomerPersona(customerBasic, lang);
        return handleResponse(wsResponseHolder);
    }


    @GetMapping("customerRM/{customerBasic}/{lang}")
    ResponseEntity<?> getCustomerRM(@PathVariable("customerBasic") String customerBasic, @PathVariable("lang") String lang) {
        WSResponseHolder<GetCustomerRMResponse> wsResponseHolder = userWebServices.getCustomerRM(customerBasic, lang);
        return handleResponse(wsResponseHolder);
    }


    @GetMapping("employeeDetails/{userID}/{lang}")
    ResponseEntity<?> getEmployeeDetail(@PathVariable("userID") String userID, @PathVariable("lang") String lang) {
        WSResponseHolder<GetEmployeeDetailResponse> wsResponseHolder = userWebServices.getEmployeeDetail(userID, lang);
        return handleResponse(wsResponseHolder);
    }

    @GetMapping("spouseDetails/{customerBasic}/{lang}")
    ResponseEntity<?> getSpouseDetails(@PathVariable("customerBasic") String customerBasic, @PathVariable("lang") String lang) {
        WSResponseHolder<GetSpouseDetailsResponse> wsResponseHolder = userWebServices.getSpouseDetails(customerBasic, lang);
        return handleResponse(wsResponseHolder);
    }

}
