package com.sh.crm.ws.handlers;

import com.sh.crm.jpa.config.BeanUtil;
import org.springframework.core.env.Environment;

public class CreditCardsServicesLoggerHandler extends BasicHandler {

    @Override
    protected void setWSDLFile() {
        this.WSDLFile = BeanUtil.getBean( Environment.class ).getProperty( "mw.creditcards.general.services.wsdl" );
    }
}
