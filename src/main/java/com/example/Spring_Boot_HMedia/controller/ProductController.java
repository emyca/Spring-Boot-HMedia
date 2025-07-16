package com.example.Spring_Boot_HMedia.controller;

import com.example.Spring_Boot_HMedia.assembler.ProductModelAssembler;
import com.example.Spring_Boot_HMedia.dto.ProductDtoRequest;
import com.example.Spring_Boot_HMedia.entity.Product;
import com.example.Spring_Boot_HMedia.service.ProductService;
import com.example.Spring_Boot_HMedia.vo.Quota;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public HttpEntity<EntityModel<Product>> create(
            @RequestBody ProductDtoRequest request) {
        Product product = service.save(request);
        EntityModel<Product> model = assembler.toModel(product);

        return ResponseEntity.created(linkTo(methodOn(ProductController.class)
                .getById(product.getId())).toUri()).body(model);
    }

    @GetMapping
    public CollectionModel<EntityModel<Product>> getAll() {
        List<EntityModel<Product>> products = service.findAll()
                .stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(products,linkTo(methodOn(ProductController.class)
                .getAll()).withRel("products")
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Product> getById(@PathVariable("id") long id) {
        return assembler.toModel(service.findById(id));
    }

    @PutMapping("/{id}")
    public HttpEntity<EntityModel<Product>> updateById(
            @PathVariable("id") long id, @RequestBody ProductDtoRequest request) {
        Product product = service.updateById(id, request);
        return new ResponseEntity<>(assembler.toModel(product), HttpStatus.OK);
    }

    @PatchMapping("/{id}/accepts")
    public HttpEntity<EntityModel<Product>> accept(
            @PathVariable("id") long id, @RequestBody Quota quota) {
        Product product = service.accept(quota.getQuota(), id);
        return new ResponseEntity<>(assembler.toModel(product), HttpStatus.OK);
    }

    @PatchMapping("/{id}/shipments")
    public HttpEntity<EntityModel<Product>> ship(
            @PathVariable("id") long id, @RequestBody Quota quota) {
        Product product = service.ship(quota.getQuota(), id);
        return new ResponseEntity<>(assembler.toModel(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
