package com.example.help_u.Provider.Data;

public class NotificationRequest {
    private String requester;
    private String provider;

    public NotificationRequest(String requester, String provider) {
        this.requester = requester;
        this.provider = provider;
    }

    public NotificationRequest(){

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
