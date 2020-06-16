package com.mboaeat.order.controller.product;

import com.mboaeat.order.domain.service.ProductService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/products")
@Tag(name = "Product API")
@Schema(description = "Provides a list of methods that retrieve products and their data")
@Validated
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

}
