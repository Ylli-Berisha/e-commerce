package org.bisha.ecommerce.services.impls;

import org.bisha.ecommerce.dtos.CategoryDto;
import org.bisha.ecommerce.dtos.SubcategoryDto;
import org.bisha.ecommerce.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommerce.exceptions.ResourceNotFoundException;
import org.bisha.ecommerce.mappers.CategoryMapper;
import org.bisha.ecommerce.mappers.SubcategoryMapper;
import org.bisha.ecommerce.repositories.CategoryRepository;
import org.bisha.ecommerce.repositories.SubcategoryRepository;
import org.bisha.ecommerce.services.SubcategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryMapper subcategoryMapper;

    public SubcategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, SubcategoryRepository subcategoryRepository, SubcategoryMapper subcategoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryMapper = subcategoryMapper;
    }


    @Override
    public List<SubcategoryDto> getSubcategoriesByCategoryName(String categoryName) {
        var category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return subcategoryMapper.toDtos(category.getSubcategories());
    }

    @Override
    public CategoryDto getSubcategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
    }

    @Override
    public CategoryDto saveSubcategory(CategoryDto category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Subcategory already exists");
        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Override
    public CategoryDto deleteSubcategory(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
        categoryRepository.deleteById(id);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto updateSubcategoryById(CategoryDto category, Long id) {
        var existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
        existingCategory.setName(category.getName());
        if (category.getDescription() != null && !category.getDescription().isBlank())
            existingCategory.setDescription(category.getDescription());
        return categoryMapper.toDto(categoryRepository.save(existingCategory));
    }

    @Override
    public CategoryDto getSubcategoryByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
    }
}