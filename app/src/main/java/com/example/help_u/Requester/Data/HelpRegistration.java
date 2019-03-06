package com.example.help_u.Requester.Data;

//번호 등록한거 서버에서 가져오는 서버 모델 부분
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
