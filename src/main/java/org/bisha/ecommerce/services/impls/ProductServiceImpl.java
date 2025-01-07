package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.SubcategoryDto;
import org.bisha.ecommerce.mappers.CategoryMapper;
import org.bisha.ecommerce.mappers.ProductMapper;
import org.bisha.ecommerce.mappers.SubcategoryMapper;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.Subcategory;
import org.bisha.ecommerce.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryMapper categoryMapper, SubcategoryMapper subcategoryMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.subcategoryMapper = subcategoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto getProductById(Long id) {
        if (id == null || id <= 0 || id > productRepository.count()) {
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
    public ProductDto deleteProductById(Long id) {
        if (id == null || id <= 0 || id > productRepository.count()) {
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
        return productMapper.toDtos(productRepository.findByNameContaining(name));
    }

    @Override
    public List<ProductDto> getProductsByCategory(CategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid category");
        }
        if (categoryRepository.findByName(categoryDto.getName()).isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        var category = categoryMapper.toEntity(categoryDto);
        return productMapper.toDtos(productRepository.findByCategory(category));
    }

    @Override
    public List<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range");
        }
        return productMapper.toDtos(productRepository.findByPriceBetween(minPrice, maxPrice));
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid brand name");
        }
        return productMapper.toDtos(productRepository.findByBrand(brand));
    }

    @Override
    public List<ProductDto> getProductsByRating(double rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Invalid rating");
        }
        return productMapper.toDtos(productRepository.findByRatingGreaterThanEqual(rating));
    }

    @Override
    public List<ProductDto> getProductsByAvailability(boolean isAvailable) {
        return productMapper.toDtos(productRepository.findByAvailable(isAvailable));
    }

    @Override
    public List<ProductDto> getProductsCreatedAfter(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now()) || date.isBefore(LocalDate.of(2000, 1, 1))) {
            throw new IllegalArgumentException("Invalid date");
        }
        return productMapper.toDtos(productRepository.findByCreatedAtAfter(date));
    }

    @Override
    public List<ProductDto> getProductsWithStockGreaterThan(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Invalid stock value");
        }
        return productMapper.toDtos(productRepository.findByStockGreaterThan(stock));
    }

    @Override
    public List<ProductDto> getProductsBySubcategory(SubcategoryDto subcategoryDto) {
        if (subcategoryDto == null || subcategoryDto.getName() == null || subcategoryDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid subcategory");
        }
        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryDto);
        return productMapper.toDtos(productRepository.findBySubcategory(subcategory));
    }
}