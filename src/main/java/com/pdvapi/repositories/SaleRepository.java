package com.pdvapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdvapi.entities.Sale;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {}
