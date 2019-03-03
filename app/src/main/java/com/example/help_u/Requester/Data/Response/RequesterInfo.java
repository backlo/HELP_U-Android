package com.example.help_u.Requester.Data.Response;

import java.util.List;

public class RequesterInfo {

    private String name;
    private String phone;
    private String address;
    private int grade;
    private int point;
    private int count;

    public RequesterInfo(String name, String phone, String address, int grade, int point, int count) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.grade = grade;
        this.point = point;
        this.count = count;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
