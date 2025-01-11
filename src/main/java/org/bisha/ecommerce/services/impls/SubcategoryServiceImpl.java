package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.mappers.CategoryMapper;
import org.bisha.ecommerce.repositories.CategoryRepository;
import org.bisha.ecommerce.services.SubcategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public SubcategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getAllSubcategoriesByCategories() {
        return categoryMapper.toDtos(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getSubcategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Subcategory not found"));
    }

    @Override
    public CategoryDto saveSubcategory(CategoryDto category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new IllegalArgumentException("Subcategory already exists");
        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Override
    public CategoryDto deleteSubcategory(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subcategory not found"));
        categoryRepository.deleteById(id);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto updateSubcategoryById(CategoryDto category, Long id) {
        var existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subcategory not found"));
        existingCategory.setName(category.getName());
        if (category.getDescription() != null && !category.getDescription().isBlank())
            existingCategory.setDescription(category.getDescription());
        return categoryMapper.toDto(categoryRepository.save(existingCategory));
    }

    @Override
    public CategoryDto getSubcategoryByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Subcategory not found"));
    }
}