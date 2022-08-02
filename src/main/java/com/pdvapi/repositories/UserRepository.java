package com.pdvapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdvapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long> { 
}
