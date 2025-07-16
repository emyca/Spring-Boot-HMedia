package com.example.Spring_Boot_HMedia.service;

import com.example.Spring_Boot_HMedia.dto.ProductDtoRequest;
import com.example.Spring_Boot_HMedia.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product save(ProductDtoRequest request);
    List<Product> findAll();
    Product findById(long id);
    Product updateById(long id, ProductDtoRequest request);
    Product accept(double quota, long id);
}
