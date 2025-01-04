package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.SubcategoryDto;
import org.bisha.ecommerce.mappers.CategoryMapper;
import org.bisha.ecommerce.mappers.ProductMapper;
import org.bisha.ecommerce.mappers.SubcategoryMapper;
import org.bisha.ecommerce.models.Category;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.Subcategory;
import org.bisha.ecommerce.repositories.ProductRepository;
import org.bisha.ecommerce.services.ProductService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final SubcategoryMapper subcategoryMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryMapper categoryMapper, SubcategoryMapper subcategoryMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.subcategoryMapper = subcategoryMapper;
    }

    @Override
    public ProductDto getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product id");
        }
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.toDtos(productRepository.findAll());
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        if (productDto == null || productDto.getName() == null || productDto.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid product details");
        }
        Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid product id");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.deleteById(id);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid product name");
        }
        return productMapper.toDtos(productRepository.findByNameContaining(name).orElseThrow(() -> new IllegalArgumentException("No products found with the given name")));
    }

    @Override
    public List<ProductDto> getProductsByCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        return productMapper.toDtos(productRepository.findByCategory(category).orElseThrow(() -> new IllegalArgumentException("No products found in the given category")));
    }

    @Override
    public List<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range");
        }
        return productMapper.toDtos(productRepository.findByPriceBetween(minPrice, maxPrice).orElseThrow(() -> new IllegalArgumentException("No products found in the given price range")));
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid brand name");
        }
        return productMapper.toDtos(productRepository.findByBrand(brand).orElseThrow(() -> new IllegalArgumentException("No products found with the given brand")));
    }

    @Override
    public List<ProductDto> getProductsByRating(double rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Invalid rating");
        }
        return productMapper.toDtos(productRepository.findByRatingGreaterThanEqual(rating).orElseThrow(() -> new IllegalArgumentException("No products found with the given rating")));
    }

    @Override
    public List<ProductDto> getProductsByAvailability(boolean isAvailable) {
        return productMapper.toDtos(productRepository.findByAvailable(isAvailable).orElseThrow(() -> new IllegalArgumentException("No products found with the given availability")));
    }

    @Override
    public List<ProductDto> getProductsByCreationDate(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now()) || date.isBefore(LocalDate.of(2000, 1, 1))) {
            throw new IllegalArgumentException("Invalid date");
        }
        return productMapper.toDtos(productRepository.findByCreatedAtAfter(date).orElseThrow(() -> new IllegalArgumentException("No products found created after the given date")));
    }

    @Override
    public List<ProductDto> getProductsByStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Invalid stock value");
        }
        return productMapper.toDtos(productRepository.findByStockGreaterThan(stock).orElseThrow(() -> new IllegalArgumentException("No products found with the given stock value")));
    }

    @Override
    public List<ProductDto> getProductsBySubcategory(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryDto);
        return productMapper.toDtos(productRepository.findBySubcategory(subcategory).orElseThrow(() -> new IllegalArgumentException("No products found in the given subcategory")));
    }
}