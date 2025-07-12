package com.example.Spring_Boot_HMedia.service;

import com.example.Spring_Boot_HMedia.dto.ProductDtoRequest;
import com.example.Spring_Boot_HMedia.entity.Product;
import com.example.Spring_Boot_HMedia.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    ProductMapper mapper;
    ProductRepository repository;

    public ProductServiceImpl(
            ProductRepository repository,
            ProductMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product save(ProductDtoRequest request) {
        return repository.save(mapper.dtoCreateToEntity(request));
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product findById(long id) {
        return repository.findById(id).orElse(null);
    }

}
