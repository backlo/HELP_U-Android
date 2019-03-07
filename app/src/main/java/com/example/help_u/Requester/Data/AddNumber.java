package com.example.help_u.Requester.Data;

//번호 등록 부분 서버 모델
public class AddNumber {

    private String requester;
    private String provider;

    public AddNumber(String requester, String provider) {
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
