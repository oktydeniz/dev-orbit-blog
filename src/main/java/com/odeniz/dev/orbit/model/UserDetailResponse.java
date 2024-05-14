package com.odeniz.dev.orbit.model;


import com.odeniz.dev.orbit.entity.User;
import com.odeniz.dev.orbit.enums.Gender;
import com.odeniz.dev.orbit.util.CommonUtil;

public class UserDetailResponse {

    private String userName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String userLevel;
    private String userAvatarUrl;

    private String birthDate;

    public UserDetailResponse(User user) {
        this.gender = user.getGender();
        this.userName = user.getUserName();
        this.userAvatarUrl = user.getAvatarUrl();
        this.phoneNumber = user.getPhoneNumber();

        this.birthDate = CommonUtil.getBirtDateFormat(user.getBirthDate());
    }

    public void setBirthDate(String data) {
        this.birthDate = data;
    }

    public String getBirthDate() {
        return this.birthDate;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

}

