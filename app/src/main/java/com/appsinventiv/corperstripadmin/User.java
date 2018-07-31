package com.appsinventiv.corperstripadmin;

import java.util.ArrayList;

/**
 * Created by AliAh on 09/07/2018.
 */

public class User {
    String username,name,password,phone,city,school,fcmKey,picUrl;

    long time;

    ArrayList<String> inGroupsList;

    public User(String username, String name, String password, String phone, String city, String school, String fcmKey, String picUrl, long time, ArrayList<String> inGroupsList) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.school = school;
        this.fcmKey = fcmKey;
        this.picUrl = picUrl;
        this.time = time;
        this.inGroupsList = inGroupsList;
    }

    public User() {
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getFcmKey() {
        return fcmKey;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ArrayList<String> getInGroupsList() {
        return inGroupsList;
    }

    public void setInGroupsList(ArrayList<String> inGroupsList) {
        this.inGroupsList = inGroupsList;
    }
}
