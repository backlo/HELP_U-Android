package com.example.help_u.model;

import java.util.ArrayList;

public class HelperRegistration {

    private String requester;
    private ArrayList<String> provider;

    public HelperRegistration(String requester, ArrayList<String> provider) {
        this.requester = requester;
        this.provider = provider;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public ArrayList<String> getProvider() {
        return provider;
    }

    public void setProvider(ArrayList<String> provider) {
        this.provider = provider;
    }
}
