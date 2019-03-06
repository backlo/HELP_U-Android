package com.example.help_u.Requester.Data;

//사용자 정보 불러오는 부분 서버 모델 부분
public class GetUserInfo {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetUserInfo(String id) {
        this.id = id;
    }
}
