package com.odeniz.dev.orbit.dto;

import com.odeniz.dev.orbit.entity.Article;

import java.util.Date;

public class ArticleResponse {

    private String title;

    private String slug;

    private String url;

    private MenuItemDTO menuItem;

    private String content;

    private Integer readTime;

    private UserDto user;

    private Date createdAt;

    public ArticleResponse(Article article) {
        this.content = article.getContent();
        this.slug = article.getSlug();
        this.url = article.getUrl();
        this.title = article.getTitle();
        this.readTime = article.getReadTime();
        this.user = new UserDto(article.getAuthor());
        this.menuItem = new MenuItemDTO(article.getCategory());
        this.createdAt = article.getCreatedAt();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MenuItemDTO getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemDTO menuItem) {
        this.menuItem = menuItem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReadTime() {
        return readTime;
    }

    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
