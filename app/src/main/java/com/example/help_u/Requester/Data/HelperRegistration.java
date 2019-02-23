package com.example.help_u.Requester.Data;

import java.util.ArrayList;

public class HelperRegistration {

    private String id;
    private ArrayList<String> provider;

    public HelperRegistration(String id, ArrayList<String> provider) {
        this.id = id;
        this.provider = provider;
    }

    public String getRequester() {
        return id;
    }

    public void setRequester(String id) {
        this.id = id;
    }

    public ArrayList<String> getProvider() {
        return provider;
    }

    public void setProvider(ArrayList<String> provider) {
        this.provider = provider;
    }
}

