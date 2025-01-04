package org.bisha.ecommerce.services;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.SubcategoryDto;
import org.bisha.ecommerce.models.Category;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.Subcategory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto getProductById(Long id);

    List<ProductDto> getAllProducts();

    ProductDto saveProduct(ProductDto productDto);

    ProductDto deleteProduct(Long id);

    List<ProductDto> getProductsByName(String name);

    List<ProductDto> getProductsByCategory(CategoryDto categoryDto);

    List<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice);

    List<ProductDto> getProductsByBrand(String brand);

    List<ProductDto> getProductsByRating(double rating);

    List<ProductDto> getProductsByAvailability(boolean isAvailable);

    List<ProductDto> getProductsByCreationDate(LocalDate date);

    List<ProductDto> getProductsByStock(int stock);

    List<ProductDto> getProductsBySubcategory(SubcategoryDto subcategoryDto);
}