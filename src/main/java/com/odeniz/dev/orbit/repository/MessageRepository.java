package com.odeniz.dev.orbit.repository;

import com.odeniz.dev.orbit.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository< Message, Long> {
}
