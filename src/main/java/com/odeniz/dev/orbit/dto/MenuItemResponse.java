package com.odeniz.dev.orbit.dto;

import com.odeniz.dev.orbit.entity.MenuItem;

public class MenuItemResponse {
       private String url;
    private  String title;
    private String slug;
    private Integer orderIndex;
    private  MenuItemSubResponse parent;

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

    public MenuItemSubResponse getParent() {
        return parent;
    }

    public void setParent(MenuItemSubResponse parent) {
        this.parent = parent;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public MenuItemResponse(MenuItem item) {
        this.title = item.getTitle();
        this.url = item.getUrl();
        this.slug = item.getSlug();
        this.orderIndex = item.getOrderIndex();
        if (item.getParent() != null){
            this.parent = new MenuItemSubResponse(item.getParent().getUrl(),item.getParent().getTitle(),item.getParent().getSlug());
        }
    }
}


