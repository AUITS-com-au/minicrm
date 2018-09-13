package com.sh.crm.config.general;

import com.sh.crm.general.Errors;

public class ResponseCode {

    private String code;
    private String msg;




    public ResponseCode(String errorCode, String errorMsg) {
        super();
        this.code = errorCode;
        this.msg = errorMsg;
    }

    public ResponseCode(Errors error) {
        this.code = error.getCode();
        this.msg = error.getDesc();
    }

    public ResponseCode() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
