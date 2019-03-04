package com.example.help_u.Requester.Data;

public class RemoveNumber {

    private String requester;
    private String phone;

    public RemoveNumber(String requester, String phone) {
        this.requester = requester;
        this.phone = phone;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
