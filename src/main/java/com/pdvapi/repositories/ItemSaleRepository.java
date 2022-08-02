package com.pdvapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdvapi.entities.ItemSale;

public interface ItemSaleRepository extends JpaRepository<ItemSale, Long> {
}
