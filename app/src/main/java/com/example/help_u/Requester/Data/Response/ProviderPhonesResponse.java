package com.example.help_u.Requester.Data.Response;

import java.util.List;

// 등록한 번호를 요청자에게 응답하는 부분
public class ProviderPhonesResponse {

    private List<String> phones;

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
