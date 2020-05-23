package com.mboaeat.order.domain.service;

import com.mboaeat.order.domain.ChangeProductPriceCollectionCommand;
import com.mboaeat.order.domain.Product;
import com.mboaeat.order.domain.ProductPrice;
import com.mboaeat.order.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product createProduct(Product product, ProductPrice price){
        product.getPricesHistory().addPrice(price);
        return productRepository.save(product);
    }

    public void updateProduct(Product product){
        getProduct(product.getId()).ifPresent(
                productToUpdate -> {
                    ;productToUpdate.setCategory(product.getCategory());
                    productToUpdate.setDescription(product.getDescription());
                    productToUpdate.setProductName(product.getProductName());
                    productRepository.save(productToUpdate);
                }
        );
    }

    @Transactional
    public void changeProductPrice(Long productId, ProductPrice productPrice){
        getProduct(productId).ifPresent(
                product -> {
                    product.applyProductPriceChangeCommand(
                            ChangeProductPriceCollectionCommand
                                    .builder()
                                    .amount(productPrice.getAmount())
                                    .product(product)
                                    .period(productPrice.getPeriod())
                                    .build(), true
                    );
                    productRepository.save(product);
                }
        );
    }


    public Optional<Product> getProduct(Long productId){
        return productRepository.findById(productId);
    }
}
