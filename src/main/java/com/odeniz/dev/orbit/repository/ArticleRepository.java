package com.odeniz.dev.orbit.repository;

import com.odeniz.dev.orbit.entity.Article;
import com.odeniz.dev.orbit.entity.MenuItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE (a.isDeleted IS NULL OR a.isDeleted = false) AND (a.isHidden IS NULL OR a.isHidden = false) ORDER BY a.createdAt DESC, a.visitCount DESC LIMIT 16")
    List<Article> getAllArticles();

    @Query("SELECT a FROM Article a WHERE (a.isDeleted IS NULL OR a.isDeleted = false) AND (a.isHidden IS NULL OR a.isHidden = false) ORDER BY a.createdAt DESC, a.visitCount DESC")
    List<Article> getAll();

    Article findBySlug(String slug);

    List<Article> findByCategory(MenuItem menuItem);

    @Query("SELECT a FROM Article a WHERE " +
            "(LOWER(a.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.content) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "(a.isDeleted IS NULL OR a.isDeleted = false) AND " +
            "(a.isHidden IS NULL OR a.isHidden = false) " +
            "ORDER BY a.createdAt DESC, a.visitCount DESC")
    List<Article> findBySearchTerm(@Param("searchTerm") String searchTerm);


}
