package com.mb_medical_clinic_be.graphql.category;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mb_medical_clinic_be.entity.Category;
import com.mb_medical_clinic_be.repository.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class CategoryQueryResolver implements GraphQLQueryResolver {

    private final CategoryRepository categoryRepository;

    public CategoryQueryResolver(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('CATEGORY_READ_PRIVILEGE')")
    public Category getCategory(@NotNull Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
    }
}
