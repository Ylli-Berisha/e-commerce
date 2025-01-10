package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.SubcategoryDto;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    ProductDto getProductById(Long id);

    List<ProductDto> getAllProducts();

    ProductDto saveProduct(ProductDto productDto);

    ProductDto addProduct(ProductDto productDto);

    ProductDto deleteProductById(Long id);

    List<ProductDto> getProductsByName(String name);

    List<ProductDto> getProductsByCategory(CategoryDto categoryDto);

    List<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice);

    List<ProductDto> getProductsByBrand(String brand);

    List<ProductDto> getProductsByRatingGreaterThanEqual(double rating);

    List<ProductDto> getProductsByAvailability(boolean isAvailable);

    List<ProductDto> getProductsCreatedAfter(LocalDate date);

    List<ProductDto> getProductsWithStockGreaterThan(int stock);

    List<ProductDto> getProductsBySubcategory(SubcategoryDto subcategoryDto);

    ProductDto updateProductById(Long id, ProductDto productDto);
}