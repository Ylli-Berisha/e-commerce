package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.mappers.CategoryMapper;
import org.bisha.ecommerce.repositories.CategoryRepository;
import org.bisha.ecommerce.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toDtos(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        if (id == null || id <= 0 || id > categoryRepository.count()) {
            throw new IllegalArgumentException("Category ID out of bounds");
        }
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public CategoryDto saveCategory(CategoryDto category) {
        if (category == null || category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Invalid category data");
        }
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new IllegalArgumentException("Category already exists");
        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Override
    public CategoryDto deleteCategory(Long id) {
        if (id == null || id <= 0 || id > categoryRepository.count()) {
            throw new IllegalArgumentException("Category ID out of bounds");
        }
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        categoryRepository.deleteById(id);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto updateCategoryById(CategoryDto category, Long id) {
        if (category == null || category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Invalid category data");
        }
        var existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        existingCategory.setName(category.getName());
        if (category.getDescription() != null && !category.getDescription().isBlank())
            existingCategory.setDescription(category.getDescription());
        return categoryMapper.toDto(categoryRepository.save(existingCategory));
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException("Invalid category name");
        }
        return categoryRepository.findByName(name)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }
}