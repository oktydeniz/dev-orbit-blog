package com.odeniz.dev.orbit.entity;


import com.odeniz.dev.orbit.dto.ArticleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article extends DateIDBaseEntity {

    private String title;
    private String slug;

    private String url;
    @Column
    private Boolean isDeleted;

    @Column
    private Boolean isHidden;

    @Column
    private Integer visitCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private MenuItem category;

    @Column(name = "article_content" , length = 100000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column
    private Integer readTime;

}
