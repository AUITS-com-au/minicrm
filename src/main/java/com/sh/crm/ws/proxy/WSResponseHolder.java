package com.sh.crm.ws.proxy;

import com.sh.ws.response.ResponseHeader;

public class WSResponseHolder<T> {

    int status;
    T value;
    ResponseHeader responseHeader;

    public WSResponseHolder(int status, T value) {
        this.status = status;
        this.value = value;
    }

    public WSResponseHolder(int status, T value, ResponseHeader responseHeader) {
        this.status = status;
        this.value = value;
        this.responseHeader = responseHeader;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    @Override
    public String toString() {
        return "WSResponseHolder{" +
                "status=" + status +
                ", value=" + value +
                '}';
    }
}
