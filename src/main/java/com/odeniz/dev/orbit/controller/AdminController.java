package com.odeniz.dev.orbit.controller;


import com.odeniz.dev.orbit.aspect.RequiresAuth;
import com.odeniz.dev.orbit.dto.ArticleDTO;
import com.odeniz.dev.orbit.dto.MenuItemResponse;
import com.odeniz.dev.orbit.dto.MenuItemSubResponse;
import com.odeniz.dev.orbit.entity.Article;
import com.odeniz.dev.orbit.entity.MenuItem;
import com.odeniz.dev.orbit.entity.Message;
import com.odeniz.dev.orbit.model.MessageRequest;
import com.odeniz.dev.orbit.service.ArticleService;
import com.odeniz.dev.orbit.service.MenuService;
import com.odeniz.dev.orbit.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequestMapping("/orbit")
@Controller
public class AdminController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final MenuService menuService;

    private final ArticleService articleService;

    private final MessageService messageService;


    public AdminController(MenuService menuService, ArticleService articleService, MessageService messageService) {
        this.menuService = menuService;
        this.articleService = articleService;
        this.messageService = messageService;
    }

    @GetMapping
    @RequiresAuth
    public String orbit(Model model){
        model.addAttribute("menuItems", menuService.getRootMenus());
        model.addAttribute("items", articleService.findAll());
        return "admin/admin";
    }

    @PostMapping("/save")
    @RequiresAuth
    public String saveMenu(@ModelAttribute MenuItem menuItem) {
        menuService.save(menuItem);

        return "redirect:/orbit";
    }

    @PostMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/orbit";
    }

    @GetMapping("deneme/edit/{id}")
    @ResponseBody
    @RequiresAuth
    public ResponseEntity<Map<String, Object>> editMenu(@PathVariable Long id, Model model) {
        MenuItem menuItem = menuService.findById(id);
        List<MenuItem> menuSubItems = menuService.findSubsBy(id);
        List<MenuItemSubResponse> responses = menuSubItems.stream()
                .map(item -> new MenuItemSubResponse(item.getUrl(), item.getTitle(), item.getSlug()))
                .collect(Collectors.toList());
        MenuItemResponse r = new MenuItemResponse(menuItem);
        Map<String, Object> data = new HashMap<>();
        data.put("menu", r);
        data.put("subMenu", responses);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    @RequiresAuth
    public String edit(@PathVariable Long id, Model model) {
            Article article = articleService.findById(id);
        List<MenuItem> menuItems = menuService.getRootMenus();
            model.addAttribute("article", article);
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("selectedCategoryId", article.getCategory().getId());
        return "admin/article_edit";
    }

    @GetMapping("/create")
    @RequiresAuth
    public String create(Model model) {
        List<MenuItem> menuItems = menuService.getRootMenus();
        model.addAttribute("menuItems", menuItems);
        return "admin/article_create";
    }

    @PostMapping("/create")
    @RequiresAuth
    public ResponseEntity<?>  createArticle(@RequestBody ArticleDTO articleDTO) {
        boolean result = articleService.createNew(articleDTO);
        if (result){
            return ResponseEntity.ok().body("Article updated successfully");

        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/send-contact")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest messageRequest){
        boolean result = messageService.save(messageRequest);
        if (result){
            return ResponseEntity.ok().body("Sent successfully");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/message")
    public String messages(Model model){
        model.addAttribute("messages", messageService.findAll());
        return "admin/message";
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<Map<String, Object>> message(@PathVariable Long id, Model model){
        Message message = messageService.findById(id);
        if (message != null){
            Map<String, Object> data = new HashMap<>();
            data.put("data", message);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@ModelAttribute ArticleDTO articleDTO) {
        String fileDownloadUri = null;
        try {
            if (articleDTO.getFile() != null && !articleDTO.getFile().isEmpty()) {
                MultipartFile file = articleDTO.getFile();
                String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
                String modifiedFileName = originalFileName.replace(" ", "_").replace("-", "_");
                Path copyLocation = Paths.get(this.uploadDir + File.separator + StringUtils.cleanPath(modifiedFileName));
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploads/")
                        .path(modifiedFileName)
                        .toUriString();
            }
            boolean result = articleService.update(articleDTO.getId(), articleDTO, fileDownloadUri);
            if(result){
                return ResponseEntity.ok("File uploaded successfully: " + fileDownloadUri);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during file upload");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during file upload");
        }
    }
}
