package com.example.Spring_Boot_HMedia.assembler;

import com.example.Spring_Boot_HMedia.controller.ProductController;
import com.example.Spring_Boot_HMedia.entity.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements
        RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product product) {

        EntityModel<Product> model = EntityModel.of(product);

        model.add(linkTo(methodOn(ProductController.class)
                .getAll()).withRel(IanaLinkRelations.COLLECTION));

        return model;
    }

}
