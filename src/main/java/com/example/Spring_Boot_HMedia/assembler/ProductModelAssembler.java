package com.example.Spring_Boot_HMedia.assembler;

import com.example.Spring_Boot_HMedia.entity.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler implements
        RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product product) {

        EntityModel<Product> model = EntityModel.of(product);

        return model;
    }

}
