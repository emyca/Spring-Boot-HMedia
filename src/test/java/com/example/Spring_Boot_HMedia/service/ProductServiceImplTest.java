package com.example.Spring_Boot_HMedia.service;

import com.example.Spring_Boot_HMedia.entity.Product;
import com.example.Spring_Boot_HMedia.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductServiceImpl service;

    @Test
    @DisplayName("Gets saved products collection that has its proper quantity.")
    void givenSavedProducts_whenFetchAll_thenGetAll() {
        // Given
        // Previously populated items
        Product product1 = new Product( 1L, "banana", "kg", 15);
        Product product2 = new Product( 2L, "mango", "pcs.", 23);
        Product product3 = new Product( 3L, "kiwi", "box", 19);
        // When
        // Calls of repository and service methods
        given(repository.findAll())
                .willReturn(List.of( product1, product2, product3 ));
        var  productList = service.findAll();
        // Then
        // Asserts that items list is not null
        assertThat(productList).isNotNull();
        // Asserts that the items list has previously populated items
        // and has specific items quantity
        assertThat(productList.size()).isEqualTo(3);
    }

}
