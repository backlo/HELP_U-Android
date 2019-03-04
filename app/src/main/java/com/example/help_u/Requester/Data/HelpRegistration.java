package com.example.help_u.Requester.Data;

public class HelpRegistration {
    private String id;

    public HelpRegistration(String id) {
        this.id = id;
    }

    public String getRequester() {
        return id;
    }

    public void setRequester(String id) {
        this.id = id;
    }

}
