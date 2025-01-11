package org.bisha.ecommerce.Controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.services.CategoryService;
import org.bisha.ecommerce.services.SubcategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    public CategoryController(CategoryService categoryService, SubcategoryService subcategoryService) {
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable @Min(1) Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public CategoryDto saveCategory(@RequestBody @Valid  CategoryDto category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/{id}")
    public CategoryDto deleteCategory(@PathVariable @Min(1) Long id) {
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategoryById(@RequestBody @Valid CategoryDto category, @PathVariable @Min(1) Long id) {
        return categoryService.updateCategoryById(category, id);
    }

    @GetMapping("/name/{name}")
    public CategoryDto getCategoryByName(@PathVariable @NotBlank String name) {
        return categoryService.getCategoryByName(name);
    }

    @GetMapping("/subcategories")
    public List<CategoryDto> getAllSubcategoriesByCategories() {
        return subcategoryService.getAllSubcategoriesByCategories();
    }

    @GetMapping("/subcategories/{id}")
    public CategoryDto getSubcategoryById(@PathVariable @Min(1) Long id) {
        return subcategoryService.getSubcategoryById(id);
    }

    @PostMapping("/subcategories")
    public CategoryDto saveSubcategory(@RequestBody @Valid CategoryDto category) {
        return subcategoryService.saveSubcategory(category);
    }

    @DeleteMapping("/subcategories/{id}")
    public CategoryDto deleteSubcategory(@PathVariable @Min(1) Long id) {
        return subcategoryService.deleteSubcategory(id);
    }

    @PutMapping("/subcategories/{id}")
    public CategoryDto updateSubcategoryById(@RequestBody @Valid CategoryDto category, @PathVariable @Min(1) Long id) {
        return subcategoryService.updateSubcategoryById(category, id);
    }

    @GetMapping("/subcategories/name/{name}")
    public CategoryDto getSubcategoryByName(@PathVariable @NotBlank String name) {
        return subcategoryService.getSubcategoryByName(name);
    }

    @GetMapping("/get-dto")
    public CategoryDto getDto() {
        return new CategoryDto();
    }
}