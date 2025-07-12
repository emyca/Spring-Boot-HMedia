package com.example.Spring_Boot_HMedia.service;

import com.example.Spring_Boot_HMedia.dto.ProductDtoRequest;
import com.example.Spring_Boot_HMedia.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product dtoCreateToEntity(ProductDtoRequest request) {
        Product product = new Product();

        long id = request.getId();
        product.setId(id);

        String name = request.getName();
        if (name != null) {
            if (!name.isBlank())
                product.setName(name);
        }

        String measure = request.getMeasure();
        if (measure != null) {
            if (!measure.isBlank())
                product.setMeasure(measure);
        }

        double stock = request.getStock();
        if (stock > 0) {
            product.setStock(stock);
        }

        return product;
    }
}
