package com.example.help_u.Requester.Data;

//도움오청을 서버로 보낼때 서버 모델 부분
public class LocationRequest {

    private String location;
    private String requester;
    private String message;
    private int count;

    public LocationRequest(String location, String requester, String message, int count) {
        this.location = location;
        this.requester = requester;
        this.message = message;
        this.count = count;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
