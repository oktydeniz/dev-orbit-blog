package com.odeniz.dev.orbit.repository;

import com.odeniz.dev.orbit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
