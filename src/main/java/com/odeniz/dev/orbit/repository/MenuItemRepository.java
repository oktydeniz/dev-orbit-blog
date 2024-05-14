package com.odeniz.dev.orbit.repository;

import com.odeniz.dev.orbit.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.*;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("SELECT m FROM MenuItem m WHERE m.parent IS NULL and (m.isDeleted IS NULL OR m.isDeleted = false) ORDER BY orderIndex ASC")
    List<MenuItem> findByParentIsNull();

    @Query("SELECT m FROM MenuItem m WHERE m.parent.id = :id")
    List<MenuItem> findChild(@Param("id") Long id);

    MenuItem findBySlug(String category);
}