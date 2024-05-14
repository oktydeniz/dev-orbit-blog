package com.odeniz.dev.orbit.dto;

import com.odeniz.dev.orbit.entity.MenuItem;

public class MenuItemDTO {


    private String url;
    private  String title;
    private String slug;


    public MenuItemDTO(MenuItem menuItem){
        this.url = menuItem.getUrl();
        this.slug = menuItem.getSlug();
        this.title = menuItem.getTitle();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
