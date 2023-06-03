package com.mb_medical_clinic_be.graphql.category;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mb_medical_clinic_be.OperationStatus;
import com.mb_medical_clinic_be.dict.DictOperationName;
import com.mb_medical_clinic_be.entity.Category;
import com.mb_medical_clinic_be.mapper.SmartMapper;
import com.mb_medical_clinic_be.repository.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@Service
public class CategoryMutationResolver implements GraphQLMutationResolver {

    private final CategoryRepository categoryRepository;

    public CategoryMutationResolver(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @PreAuthorize("hasAuthority('CATEGORY_ADD_PRIVILEGE')")
    public OperationStatus addCategory(CategoryInput categoryInput) {
        return saveCategory(null, categoryInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('CATEGORY_MODIFY_PRIVILEGE')")
    public OperationStatus updateCategory(@NotNull Integer categoryInputId, CategoryInput categoryInput) {
        return saveCategory(categoryInputId, categoryInput);
    }

    @Transactional
    @PreAuthorize("hasAuthority('CATEGORY_DELETE_PRIVILEGE')")
    public OperationStatus deleteCategory(@NotNull Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

        OperationStatus opStatus = new OperationStatus(Category.class.getSimpleName(), DictOperationName.DELETE.getCode());
        opStatus.setRecordId(categoryId);

        categoryRepository.delete(category);

        return opStatus.setSuccess(!categoryRepository.existsById(categoryId));
    }

    protected OperationStatus saveCategory(Integer categoryId, CategoryInput categoryInput) {
        boolean adding = (categoryId == null);
        Category category = (adding ? new Category() : categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new));

        OperationStatus opStatus = new OperationStatus(Category.class.getSimpleName(), adding ? DictOperationName.ADD.getCode() : DictOperationName.UPDATE.getCode());

        SmartMapper.transferData(categoryInput, category);

        Category categorySaved = categoryRepository.save(category);
        Integer id = categorySaved.getCategoryId();
        opStatus.setRecordId(id).setSuccess(id != null);
        return opStatus;
    }
}
