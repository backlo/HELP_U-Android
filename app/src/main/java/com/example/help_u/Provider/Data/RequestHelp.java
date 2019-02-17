package com.example.help_u.Provider.Data;


public class RequestHelp {

    private String requester;
    private String provider;

    public RequestHelp(){}

    public RequestHelp(String requester, String provider) {
        this.requester = requester;
        this.provider = provider;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}

