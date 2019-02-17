package com.example.help_u.model;

public class UserInfo {

    private String id;
    private String password;
    private String name;
    private String phone;
    private String personal_no;
    private int gender;
    private String user_type;
    private String token;

    public UserInfo() {
    }

    public UserInfo(String id) {
        this.id = id;
    }

    public UserInfo(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public UserInfo(String id, String password, String name, String phone, String personal_no, int gender, String user_type, String token) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.personal_no = personal_no;
        this.gender = gender;
        this.user_type = user_type;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonal_no() {
        return personal_no;
    }

    public void setPersonal_no(String personal_no) {
        this.personal_no = personal_no;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
