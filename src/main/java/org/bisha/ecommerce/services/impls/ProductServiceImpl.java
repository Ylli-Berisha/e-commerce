package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.dtos.ProductDto;
import org.bisha.ecommerce.dtos.SubcategoryDto;
import org.bisha.ecommerce.mappers.CategoryMapper;
import org.bisha.ecommerce.mappers.ProductMapper;
import org.bisha.ecommerce.mappers.ReviewMapper;
import org.bisha.ecommerce.mappers.SubcategoryMapper;
import org.bisha.ecommerce.models.Product;
import org.bisha.ecommerce.models.Subcategory;
import org.bisha.ecommerce.repositories.CategoryRepository;
import org.bisha.ecommerce.repositories.ProductRepository;
import org.bisha.ecommerce.repositories.SubcategoryRepository;
import org.bisha.ecommerce.services.ProductService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final SubcategoryMapper subcategoryMapper;
    private final CategoryRepository categoryRepository;
    private final ReviewMapper reviewMapper;
    private final SubcategoryRepository subcategoryRepository;


    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryMapper categoryMapper, SubcategoryMapper subcategoryMapper, CategoryRepository categoryRepository, ReviewMapper reviewMapper, SubcategoryRepository subcategoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.subcategoryMapper = subcategoryMapper;
        this.categoryRepository = categoryRepository;
        this.reviewMapper = reviewMapper;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public ProductDto getProductById(Long id) {
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
        Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    public ProductDto addProduct(ProductDto productDto) {
        List<Product> products = productRepository.findByNameContaining(productDto.getName());
        var existingProduct = products.stream()
                .filter(product -> product.getName().equals(productDto.getName()) &&
                        product.getBrand().equals(productDto.getBrand()) &&
                        product.getCategory().equals(productDto.getCategory()))
                .findFirst();

        if (existingProduct.isEmpty()) {
            productDto.setStock(1);
            return saveProduct(productDto);
        } else {
            var product = existingProduct.get();
            product.setStock(product.getStock() + 1);
            return productMapper.toDto(productRepository.save(product));
        }
    }

    @Override
    public ProductDto deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (product.getStock() > 1) {
            product.setStock(product.getStock() - 1);
        }
        productRepository.deleteById(id);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productMapper.toDtos(productRepository.findByNameContaining(name));
    }

    @Override
    public List<ProductDto> getProductsByCategory(CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.getName()).isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        var category = categoryMapper.toEntity(categoryDto);
        return productMapper.toDtos(productRepository.findByCategory(category));
    }

    @Override
    public List<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productMapper.toDtos(productRepository.findByPriceBetween(minPrice, maxPrice));
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return productMapper.toDtos(productRepository.findByBrand(brand));
    }

    @Override
    public List<ProductDto> getProductsByRatingGreaterThanEqual(double rating) {
        return productMapper.toDtos(productRepository.findByRatingGreaterThanEqual(rating));
    }

    @Override
    public List<ProductDto> getProductsByAvailability(boolean isAvailable) {
        return productMapper.toDtos(productRepository.findByAvailable(isAvailable));
    }

    @Override
    public List<ProductDto> getProductsCreatedAfter(LocalDate date) {
        return productMapper.toDtos(productRepository.findByCreatedAtAfter(date));
    }

    @Override
    public List<ProductDto> getProductsWithStockGreaterThan(int stock) {
        return productMapper.toDtos(productRepository.findByStockGreaterThan(stock));
    }

    @Override
    public List<ProductDto> getProductsBySubcategory(SubcategoryDto subcategoryDto) {
        if (subcategoryRepository.findByName(subcategoryDto.getName()).isEmpty()) {
            throw new IllegalArgumentException("Subcategory not found");
        }
        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryDto);
        return productMapper.toDtos(productRepository.findBySubcategory(subcategory));
    }

    @Override
    public ProductDto updateProductById(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageURLs(productDto.getImageUrls());
        product.setReviews(reviewMapper.toEntities(productDto.getReviews()));
        product.setStock(productDto.getStock());
        product.setCategory(productDto.getCategory());
        product.setBrand(productDto.getBrand());
        product.setRating(productDto.getRating());
        product.setSubcategory(productDto.getSubcategory());
        product.setAvailable(productDto.getAvailable());
        product.setCreatedAt(productDto.getCreatedAt());
        return productMapper.toDto(productRepository.save(product));
    }
}