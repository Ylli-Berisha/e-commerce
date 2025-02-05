package org.bisha.ecommercefinal.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.bisha.ecommercefinal.dtos.CategoryDto;
import org.bisha.ecommercefinal.dtos.ImageDto;
import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.SubcategoryDto;
import org.bisha.ecommercefinal.helpers.FileHelper;
import org.bisha.ecommercefinal.mappers.ProductMapper;
import org.bisha.ecommercefinal.models.Image;
import org.bisha.ecommercefinal.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {
    private final ProductService productService;
    private final FileHelper fileHelper;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, FileHelper fileHelper, ProductMapper productMapper) {
        this.productService = productService;
        this.fileHelper = fileHelper;
        this.productMapper = productMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto, @RequestParam("photoFile") MultipartFile photoFile    ) {
        productDto.setId(null);
        productDto.setCreatedAt(LocalDate.now());
        productDto.setAvailable(true);
        productDto.setRating(0.0);
        String photoFileName = null;
        if (!photoFile.isEmpty()) {
            try {
                var fileName = fileHelper.uploadFile("target/classes/static/img/products"
                        , photoFile.getOriginalFilename()
                        , photoFile.getBytes());
                photoFileName = String.format("/static/img/products/" + fileName);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        ProductDto createdProduct = productService.addProduct(productDto);
        if (createdProduct == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        productService.addImageToProduct(createdProduct.getId(), photoFileName);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable @Min(1) Long id, @Valid @RequestBody ProductDto productDetails) {
        ProductDto updatedProduct = productService.updateProductById(id, productDetails);
        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable @Min(1) Long id) {
        ProductDto deletedProduct = productService.deleteProductById(id);
        return (deletedProduct != null) ? ResponseEntity.ok(deletedProduct) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> getProductsByName(@RequestParam @NotBlank @Size(min = 1, max = 100) String name) {
        return ResponseEntity.ok(productService.getProductsByName(name));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@NotNull @PathVariable String categoryName) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(categoryName);
        return ResponseEntity.ok(productService.getProductsByCategory(categoryDto));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(@RequestParam @DecimalMin("0.0") double minPrice, @RequestParam @DecimalMin("0.0") double maxPrice) {
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range");
        }
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/brand")
    public ResponseEntity<List<ProductDto>> getProductsByBrand(@RequestParam @NotBlank @Size(max = 100) String brand) {
        return ResponseEntity.ok(productService.getProductsByBrand(brand));
    }

    @GetMapping("/rating")
    public ResponseEntity<List<ProductDto>> getProductsByRatingGreaterThanEqual(@RequestParam @DecimalMin("1") @DecimalMax("5.0") double rating) {
        return ResponseEntity.ok(productService.getProductsByRatingGreaterThanEqual(rating));
    }

    @GetMapping("/availability")
    public ResponseEntity<List<ProductDto>> getProductsByAvailability(@RequestParam boolean isAvailable) {
        return ResponseEntity.ok(productService.getProductsByAvailability(isAvailable));
    }

    @GetMapping("/created-after")
    public ResponseEntity<List<ProductDto>> getProductsCreatedAfter(@RequestParam @PastOrPresent LocalDate date) {
        return ResponseEntity.ok(productService.getProductsCreatedAfter(date));
    }

    @GetMapping("/stock-greater-than")
    public ResponseEntity<List<ProductDto>> getProductsWithStockGreaterThan(@RequestParam @Min(0) int stock) {
        return ResponseEntity.ok(productService.getProductsWithStockGreaterThan(stock));
    }

    @GetMapping("/subcategory/{subcategoryName}")
    public ResponseEntity<List<ProductDto>> getProductsBySubcategory(@NotNull @PathVariable String subcategoryName) {
        SubcategoryDto subcategoryDto = new SubcategoryDto();
        subcategoryDto.setName(subcategoryName);
        return ResponseEntity.ok(productService.getProductsBySubcategory(subcategoryDto));
    }

    @GetMapping("/get-dto")
    public ResponseEntity<ProductDto> getDto() {
        return ResponseEntity.ok(new ProductDto());
    }
}
