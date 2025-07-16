package com.example.Spring_Boot_HMedia.service;

import com.example.Spring_Boot_HMedia.dto.ProductDtoRequest;
import com.example.Spring_Boot_HMedia.entity.Product;
import com.example.Spring_Boot_HMedia.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Product updateById(long id, ProductDtoRequest request) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            Product productToUpdate =
                    mapper.dtoUpdateToEntity(id, request,
                            optional.get());
            repository.save(productToUpdate);
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public Product accept(double quota, long id) {
        repository.accept(quota, id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public Product ship(double quota, long id) {
        repository.ship(quota, id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
