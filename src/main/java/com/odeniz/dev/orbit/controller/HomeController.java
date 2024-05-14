package com.odeniz.dev.orbit.controller;

import com.odeniz.dev.orbit.entity.Article;
import com.odeniz.dev.orbit.entity.MenuItem;
import com.odeniz.dev.orbit.service.ArticleService;
import com.odeniz.dev.orbit.service.MenuService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping({"/","index"})
public class HomeController {

    private final MenuService menuService;
    private final ArticleService articleService;

    public HomeController(MenuService menuService, ArticleService articleService) {
        this.menuService = menuService;
        this.articleService = articleService;
    }

    @GetMapping
    public String getHome(Model model, Authentication authentication){
        List<Article>  articles = articleService.findAll();
        Article head = articles.get(0);
        articles.remove(0);
        model.addAttribute("articles", articles);
        model.addAttribute("head", head);
        return "home";
    }

    @GetMapping("/article/{slug}")
    public String getArticle(@PathVariable String slug, Model model){
        Article article = articleService.findBySlug(slug);
        if (article != null){
            model.addAttribute("article", article);
            return "detail";
        }
        return "error_404";
    }

    @GetMapping("/articles")
    public String articles(Model model){
        List<Article> articles = articleService.findAllArticle();
        model.addAttribute("articles", articles);
        return "articles";
    }

    @GetMapping("/{category}")
    public String articlesByCategory(@PathVariable String category, Model model){
        if (category.equals("about-me") || category.equals("contact")){
            return "contact";
        }
        List<Article> articles = articleService.findAllArticle(category);
        if (articles != null){
            MenuItem menu = menuService.findBySlug(category);
            model.addAttribute("articles", articles);
            model.addAttribute("category", menu);
            return "articles";
        }
        return "error_404";
    }
}
