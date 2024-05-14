package com.odeniz.dev.orbit.service;

import com.odeniz.dev.orbit.configration.JWTUserDetail;
import com.odeniz.dev.orbit.dto.ArticleDTO;
import com.odeniz.dev.orbit.dto.ArticleResponse;
import com.odeniz.dev.orbit.entity.Article;
import com.odeniz.dev.orbit.entity.MenuItem;
import com.odeniz.dev.orbit.entity.User;
import com.odeniz.dev.orbit.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final EntityManager entityManager;

    private final UserService userService;

    private final MenuService menuService;


    public ArticleService(ArticleRepository articleRepository, UserService userService, MenuService menuService, EntityManager entityManager) {
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.menuService = menuService;
        this.entityManager = entityManager;
    }

    public List<Article> findAll(){
        return articleRepository.getAllArticles();
    }

    public List<Article> findAllArticle(){
        return articleRepository.getAll();
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public boolean update(Long id, ArticleDTO articleDTO, String filePath) {
        if (id != null){
            Article article = articleRepository.findById(id).orElse(null);
            if (article != null){
                article.setContent(articleDTO.getContent());
                article.setTitle(articleDTO.getTitle());
                article.setSlug(articleDTO.getSlug());
                article.setUrl(articleDTO.getUrl());
                article.setReadTime(articleDTO.getReadTime());
                MenuItem menuItem = menuService.findById(Long.parseLong(articleDTO.getCategory()));
                if (menuItem != null){
                    article.setCategory(menuItem);
                }
                if (articleDTO.getFile() != null && !articleDTO.getFile().isEmpty()){
                    article.setUrl(filePath);
                }
                articleRepository.save(article);
                return true;
            }
        }else {
            if (articleDTO.getFile() != null && !articleDTO.getFile().isEmpty() && filePath != null && !filePath.isEmpty()){
               articleDTO.setUrl(filePath);
               return createNew(articleDTO);
            }

        }
        return false;
    }

    public boolean createNew(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setIsDeleted(false);
        article.setSlug(articleDTO.getSlug());
        article.setTitle(articleDTO.getTitle());
        article.setUrl(articleDTO.getUrl());
        article.setReadTime(articleDTO.getReadTime());
        article.setVisitCount(0);
        article.setContent(articleDTO.getContent());
        article.setIsHidden(false);
        MenuItem menuItem = menuService.findById(Long.parseLong(articleDTO.getCategory()));
        if (menuItem != null){
            article.setCategory(menuItem);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserDetail user = (JWTUserDetail) authentication.getPrincipal();
        User currentUser = userService.findUserByUserId(user.getId());
        article.setAuthor(currentUser);
        articleRepository.save(article);
        return true;
    }

    public Article findBySlug(String slug) {
        return articleRepository.findBySlug(slug);

    }

    public List<Article> findAllArticle(String category) {
        MenuItem menuItem = menuService.findBySlug(category);
        if (menuItem != null){
            return articleRepository.findByCategory(menuItem);
        }
        return null;
    }

    public List<ArticleResponse> searchByQuery(String query) {
        return articleRepository.findBySearchTerm(query)
                .stream()
                .map(ArticleResponse::new) // using the constructor to map
                .collect(Collectors.toList());
    }
}
