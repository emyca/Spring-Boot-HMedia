package com.example.Spring_Boot_HMedia.service;

import com.example.Spring_Boot_HMedia.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findAll();
}
