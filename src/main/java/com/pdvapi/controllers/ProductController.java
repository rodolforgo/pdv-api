package com.pdvapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdvapi.entities.Product;
import com.pdvapi.repositories.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> put(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna status 204 (deleção)
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
