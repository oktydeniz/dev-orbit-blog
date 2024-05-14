package com.odeniz.dev.orbit.controller;

import com.odeniz.dev.orbit.dto.ArticleResponse;
import com.odeniz.dev.orbit.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/find")
@RestController
public class SearchController {


    private final ArticleService articleService;

    public SearchController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<String> simpleSearch() {
        return ResponseEntity.ok("Search endpoint is working");
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("query") String query) {
        List<ArticleResponse> searchResults = articleService.searchByQuery(query);
        if (searchResults != null){
            Map<String, Object> data = new HashMap<>();
            data.put("data", searchResults);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

}
