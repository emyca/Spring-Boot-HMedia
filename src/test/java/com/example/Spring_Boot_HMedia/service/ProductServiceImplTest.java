package com.example.Spring_Boot_HMedia.service;

import com.example.Spring_Boot_HMedia.dto.ProductDtoRequest;
import com.example.Spring_Boot_HMedia.entity.Product;
import com.example.Spring_Boot_HMedia.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductMapper mapper;

    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductServiceImpl service;

    @Test
    @DisplayName("Gets proper product saving it.")
    void whenSaveProduct_thenThoseProductReturned() {
        // Given
        // Mocks item to save
        ProductDtoRequest productDtoRequest = new ProductDtoRequest(null, "banana", "kg", 15);
        Product productToSave = new Product(null, "banana", "kg", 15);
        Product productSaved = new Product(1L, "banana", "kg", 15);
        // When
        // Mocks mapper, repository and service methods calls
        when(mapper.dtoCreateToEntity(productDtoRequest)).thenReturn(productToSave);
        when(repository.save(productToSave)).thenReturn(productSaved);
        Product result = service.save(productDtoRequest);
        // Then
        // Asserts/verify that mocked method call has been invoked
        verify(repository, times(1)).save(productToSave);
        // Asserts that item is not null
        assertNotNull(result);
        // Asserts item to save and item returned are the same
        assertEquals(result, productSaved);
    }

    @Test
    @DisplayName("Gets saved products collection that has its proper quantity.")
    void givenSavedProducts_whenFetchAll_thenGetAll() {
        // Given
        // Mocks previously populated items
        Product product1 = new Product(1L, "banana", "kg", 15);
        Product product2 = new Product(2L, "mango", "pcs.", 23);
        Product product3 = new Product(3L, "kiwi", "box", 19);
        // When
        // Mocks repository and service methods calls
        given(repository.findAll())
                .willReturn(List.of(product1, product2, product3));
        var productList = service.findAll();
        // Then
        // Asserts that items list is not null
        assertThat(productList).isNotNull();
        // Asserts that the items list has previously populated items
        // and has specific items quantity
        assertThat(productList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Gets saved product by id properly.")
    public void whenGetById_thenProperProductReturned() {
        // Given
        // Mocks previously saved item
        long productId = 1L;
        Product mockProduct = new Product(productId, "banana", "kg", 15);
        // When
        // Mocks repository and service methods calls
        when(repository.findById(productId)).thenReturn(Optional.of(mockProduct));
        var result = service.findById(productId);
        // Asserts that item is not null
        assertNotNull(result);
        // Asserts that retrieved item is proper item
        assertEquals(mockProduct, result);
    }

    @Test
    @DisplayName("Gets updated by id product properly.")
    public void givenGotProductById_whenUpdateById_thenReturnUpdatedProduct() {
        // Given
        long productId = 1L;
        // Mocks request to update item by id
        ProductDtoRequest productDtoRequest = new ProductDtoRequest(null, "banana", "kg", 27);
        // Mocks previously saved item to update it
        Product savedProduct = new Product(productId, "banana", "kg", 15);
        // Mocks item to update
        Product productToUpdate = new Product(1L, "banana", "kg", 27);
        // Mocks updated item
        Product updatedProduct = new Product(1L, "banana", "kg", 27);
        // When
        // Mocks mapper, repository and service methods calls
        when(repository.findById(productId)).thenReturn(Optional.of(savedProduct));
        when(mapper.dtoUpdateToEntity(productId, productDtoRequest, savedProduct)).thenReturn(productToUpdate);
        when(repository.save(productToUpdate)).thenReturn(updatedProduct);
        when(repository.findById(productId)).thenReturn(Optional.of(updatedProduct));
        var result = service.updateById(productId, productDtoRequest);
        // Then
        // Asserts/verify that mocked method call has been invoked
        verify(repository, times(1)).save(productToUpdate);
        // Asserts that item is not null
        assertNotNull(result);
        // Asserts item to update and item returned are the same
        assertEquals(result, updatedProduct);
    }

    @Test
    @DisplayName("Item is null after deleting by id.")
    public void givenProductId_whenDeleteProduct_thenTheProductIsNull(){
        // Given
        long productId = 1L;
        // Mocks repository method call
        willDoNothing().given(repository).deleteById(productId);
        // When
        // Mocks service method call
        service.deleteById(productId);
        var result = service.findById(productId);
        // Then
        // Asserts/verify that mocked method call has been invoked
        verify(repository, times(1)).deleteById(productId);
        // Asserts that item is null
        assertNull(result);
    }

    @Test
    @DisplayName("Increase item quota by item id.")
    public void givenQuota_whenAccept_thenIncreaseItemQuota() {
        // Given
        double quota = 8;
        long id = 1L;
        Product savedProduct = new Product(id, "banana", "kg", 15);
        Product updatedProduct = new Product(id, "banana", "kg", 23);
        // Mocks repository and service methods calls
        when(repository.findById(id)).thenReturn(Optional.of(savedProduct));
        var _savedProduct = service.findById(id);
        willDoNothing().given(repository).accept(quota, id);
        when(repository.findById(id)).thenReturn(Optional.of(updatedProduct));
        var _updatedProduct = service.accept(quota, id);
        // Then
        double quotaDelta = _updatedProduct.getStock() - _savedProduct.getStock();
        // Asserts/verify that mocked method call has been invoked
        verify(repository, times(1)).accept(quota, id);
        // Asserts the increasing delta is correct
        assertEquals(quota, quotaDelta);
    }

    @Test
    @DisplayName("Decrease item quota by item id.")
    public void givenQuota_whenShip_thenDecreaseItemQuota() {
        // Given
        double quota = 4;
        long id = 1L;
        Product savedProduct = new Product(id, "banana", "kg", 15);
        Product updatedProduct = new Product(id, "banana", "kg", 11);
        // Mocks repository and service methods calls
        when(repository.findById(id)).thenReturn(Optional.of(savedProduct));
        var _savedProduct = service.findById(id);
        willDoNothing().given(repository).ship(quota, id);
        when(repository.findById(id)).thenReturn(Optional.of(updatedProduct));
        var _updatedProduct = service.ship(quota, id);
        // Then
        double quotaDelta = _savedProduct.getStock() - _updatedProduct.getStock();
        // Asserts/verify that mocked method call has been invoked
        verify(repository, times(1)).ship(quota, id);
        // Asserts the decreasing delta is correct
        assertEquals(quota, quotaDelta);
    }

}
