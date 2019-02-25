package com.example.help_u.Requester.Data;

public class LocationRequest {

    private String location;
    private String requester;

    public LocationRequest(String location, String requester) {
        this.location = location;
        this.requester = requester;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }
}
