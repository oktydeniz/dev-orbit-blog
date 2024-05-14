package com.odeniz.dev.orbit.model;

public class MessageRequest {

    private String mail;

    private String content;

    private String fullName;

    private String about;

    public MessageRequest(String mail, String content, String fullName, String about) {
        this.mail = mail;
        this.content = content;
        this.fullName = fullName;
        this.about = about;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
