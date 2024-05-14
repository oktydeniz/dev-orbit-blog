package com.odeniz.dev.orbit.dto;

import com.odeniz.dev.orbit.entity.User;

public class UserDto {

    private String userName;

    private String title;

    private String avatarUrl;

    public UserDto(User user){
        this.userName = user.getUserName();
        this.avatarUrl = user.getAvatarUrl();
        this.title = user.getTitle();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
