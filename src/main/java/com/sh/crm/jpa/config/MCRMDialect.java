package com.sh.crm.jpa.config;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MCRMDialect extends org.hibernate.dialect.SQLServer2012Dialect {

    public MCRMDialect() {
        super();
    }
}
