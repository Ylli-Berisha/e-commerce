package org.bisha.ecommercefinal.services.impls;

import org.bisha.ecommercefinal.dtos.SubcategoryDto;
import org.bisha.ecommercefinal.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.SubcategoryMapper;
import org.bisha.ecommercefinal.repositories.CategoryRepository;
import org.bisha.ecommercefinal.repositories.SubcategoryRepository;
import org.bisha.ecommercefinal.services.SubcategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryMapper subcategoryMapper;

    public SubcategoryServiceImpl(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, SubcategoryMapper subcategoryMapper) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryMapper = subcategoryMapper;
    }

    @Override
    public List<SubcategoryDto> getAllSubcategories() {
        return subcategoryMapper.toDtoList(subcategoryRepository.findAll());
    }

    @Override
    public List<SubcategoryDto> getSubcategoriesByCategoryName(String categoryName) {
        var category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return subcategoryMapper.toDtoList(category.getSubcategories());
    }

    @Override
    public SubcategoryDto getSubcategoryById(Long id) {
        return subcategoryRepository.findById(id)
                .map(subcategoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
    }

    @Override
    public SubcategoryDto saveSubcategory(SubcategoryDto subcategory) {
        if (subcategoryRepository.findByName(subcategory.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Subcategory already exists");
        }
        return subcategoryMapper.toDto(subcategoryRepository.save(subcategoryMapper.toEntity(subcategory)));
    }

    @Override
    public SubcategoryDto deleteSubcategory(Long id) {
        var subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
        categoryRepository.deleteById(id);
        return subcategoryMapper.toDto(subcategory);
    }

    @Override
    public SubcategoryDto updateSubcategoryById(SubcategoryDto subcategory, Long id) {
        var existingSubcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
        existingSubcategory.setName(subcategory.getName());
        if (subcategory.getDescription() != null && !subcategory.getDescription().isBlank())
            existingSubcategory.setDescription(subcategory.getDescription());
        return subcategoryMapper.toDto(subcategoryRepository.save(existingSubcategory));
    }

    @Override
    public SubcategoryDto getSubcategoryByName(String name) {
        return subcategoryRepository.findByName(name)
                .map(subcategoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
    }
}