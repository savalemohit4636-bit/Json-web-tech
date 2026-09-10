package com.mohit.Spring_Sec_demo.dao;

import com.mohit.Spring_Sec_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
