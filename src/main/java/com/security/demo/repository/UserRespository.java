package com.security.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.demo.entity.Role;
import com.security.demo.entity.user;



public interface UserRespository extends JpaRepository<user,Integer> {
    Optional<user> findByEmail(String username);
    user findByRole(Role role);
}
