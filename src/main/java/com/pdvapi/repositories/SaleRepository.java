package com.pdvapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdvapi.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {    
}
