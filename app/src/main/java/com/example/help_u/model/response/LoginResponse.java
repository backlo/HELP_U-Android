package com.example.help_u.model.response;

import com.example.help_u.model.Parameter;

public class LoginResponse implements Parameter {

    private String user_type;

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
