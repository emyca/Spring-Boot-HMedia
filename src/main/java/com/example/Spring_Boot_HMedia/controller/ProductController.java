package com.example.Spring_Boot_HMedia.controller;

import com.example.Spring_Boot_HMedia.assembler.ProductModelAssembler;
import com.example.Spring_Boot_HMedia.entity.Product;
import com.example.Spring_Boot_HMedia.service.ProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    private final ProductService service;
    private final ProductModelAssembler assembler;

    public ProductController(ProductService service, ProductModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Product>> getAll() {
        List<EntityModel<Product>> products = service.findAll()
                .stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(products,linkTo(methodOn(ProductController.class)
                .getAll()).withRel("products")
        );
    }
}
