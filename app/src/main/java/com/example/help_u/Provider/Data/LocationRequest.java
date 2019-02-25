package com.example.help_u.Provider.Data;

import com.google.gson.annotations.SerializedName;

public class LocationRequest {

    @SerializedName("provider")
    private String provider;
    @SerializedName("location")
    private String location;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocationRequest(String provider, String location) {
        this.provider = provider;
        this.location = location;
    }
}
