package org.bisha.ecommerce.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.SubcategoryDto;
import org.bisha.ecommerce.services.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable @Min(0) Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/add")
    public ProductDto addProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @PutMapping("update/{id}")
    public ProductDto updateProduct(@PathVariable @Min(1) Long id, @Valid @RequestBody ProductDto productDetails) {
        return productService.updateProductById(id, productDetails);
    }

    @DeleteMapping("delete/{id}")
    public void deleteProduct(@PathVariable @Min(1) Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping("/search")
    public List<ProductDto> getProductsByName(@RequestParam @NotBlank @Size(min = 1, max = 100) String name) {
        return productService.getProductsByName(name);
    }

    @GetMapping("/category")
    public List<ProductDto> getProductsByCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return productService.getProductsByCategory(categoryDto);
    }

    @GetMapping("/price-range")
    public List<ProductDto> getProductsByPriceRange(@RequestParam @DecimalMin("0.0") double minPrice, @RequestParam @DecimalMin("0.0") double maxPrice) {
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range");
        }
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/brand")
    public List<ProductDto> getProductsByBrand(@RequestParam @NotBlank @Size(max = 100) String brand) {
        return productService.getProductsByBrand(brand);
    }

    @GetMapping("/rating")
    public List<ProductDto> getProductsByRatingGreaterThanEqual(@RequestParam @DecimalMin("1") @DecimalMax("5.0") double rating) {
        return productService.getProductsByRatingGreaterThanEqual(rating);
    }

    @GetMapping("/availability")
    public List<ProductDto> getProductsByAvailability(@RequestParam boolean isAvailable) {
        return productService.getProductsByAvailability(isAvailable);
    }

    @GetMapping("/created-after")
    public List<ProductDto> getProductsCreatedAfter(@RequestParam @PastOrPresent LocalDate date) {
        return productService.getProductsCreatedAfter(date);
    }

    @GetMapping("/stock-greater-than")
    public List<ProductDto> getProductsWithStockGreaterThan(@RequestParam @Min(0) int stock) {
        return productService.getProductsWithStockGreaterThan(stock);
    }

    @GetMapping("/subcategory")
    public List<ProductDto> getProductsBySubcategory(@Valid @RequestBody SubcategoryDto subcategoryDto) {
        return productService.getProductsBySubcategory(subcategoryDto);
    }

    @GetMapping("/get-dto")
    public ProductDto getDto() {
        return new ProductDto();
    }
}