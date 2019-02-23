package com.example.help_u.Provider.Data;


import java.lang.reflect.Parameter;

public class ServerResponse {

    private int resultCode;
    private String message;
    private Object param;

    public ServerResponse(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


