package com.odeniz.dev.orbit.service;

import com.odeniz.dev.orbit.entity.MenuItem;
import com.odeniz.dev.orbit.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;

    public MenuService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getRootMenus() {
        return menuItemRepository.findByParentIsNull();
    }

    public List<MenuItem> getAllMenus() {
        return menuItemRepository.findAll();
    }

    public MenuItem save(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu item not found"));
    }

    public List<MenuItem> findSubsBy(Long id) {
        return menuItemRepository.findChild(id);
    }

    public void delete(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        menuItem.setIsDeleted(true);
        menuItemRepository.save(menuItem);
    }

    public MenuItem findBySlug(String category) {
        return menuItemRepository.findBySlug(category);
    }
}