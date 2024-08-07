package com.myfund.models.DTOs.mappers;

import com.myfund.models.Category;
import com.myfund.models.DTOs.CategoryDTO;
import com.myfund.models.DTOs.CreateCategoryDTO;
import com.myfund.models.DTOs.CreateSubCategoryDTO;
import com.myfund.models.DTOs.SubCategoryDTO;
import com.myfund.models.SubCategory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    void testCreateCategoryDTOMapToCategory() {

        CreateSubCategoryDTO subCategoryDTO = new CreateSubCategoryDTO();
        subCategoryDTO.setName("SubCategory1");

        CreateCategoryDTO categoryDTO = new CreateCategoryDTO();
        categoryDTO.setName("Category1");
        categoryDTO.setSubCategories(Arrays.asList(subCategoryDTO));

        Category category = CategoryMapper.toModel(categoryDTO);

        assertEquals("Category1", category.getName(), "Category name does not match");
        assertTrue(category.getSubCategories().size() == 1, "SubCategory list size does not match");
        assertEquals("SubCategory1", category.getSubCategories().get(0).getName(), "SubCategory name does not match");
    }

    @Test
    void testCategoryMapToCategoryDTO() {

        SubCategory subCategory = SubCategory.builder().id(1L).name("SubCategory1").build();

        Category category = Category.builder()
                .name("Test Category")
                .id(1L)
                .subCategories(Arrays.asList(subCategory))
                .build();

        CategoryDTO categoryDTO = CategoryMapper.toDTO(category);

        assertNotNull(categoryDTO, "CategoryDTO should not be null");
        assertEquals(category.getId(), categoryDTO.getId(), "Category ID does not match");
        assertEquals(category.getName(), categoryDTO.getName(), "Category name does not match");
        assertNotNull(categoryDTO.getSubCategories(), "SubCategories list should not be null");
        assertEquals(1, categoryDTO.getSubCategories().size(), "SubCategories list size does not match");
        assertEquals(subCategory.getId(), categoryDTO.getSubCategories().get(0).getId(), "SubCategory ID does not match");
        assertEquals(subCategory.getName(), categoryDTO.getSubCategories().get(0).getName(), "SubCategory name does not match");
    }
    @Test
    void testCategoryListMapToCategoryListDTO() {

        SubCategory subCategory1 = SubCategory.builder().id(1L).name("SubCategory1").build();
        Category category1 = Category.builder()
                .name("Test Category")
                .id(1L)
                .subCategories(Arrays.asList(subCategory1))
                .build();

        List<Category> categories = Arrays.asList(category1);

        List<CategoryDTO> categoryDTOs = CategoryMapper.toListDTO(categories);
        CategoryDTO categoryDTO = categoryDTOs.get(0);

        assertNotNull(categoryDTOs, "The returned list should not be null");
        assertEquals(1, categoryDTOs.size(), "The size of the returned list does not match the expected value");
        assertEquals(category1.getId(), categoryDTO.getId(), "Category ID does not match");
        assertEquals(category1.getName(), categoryDTO.getName(), "Category name does not match");
        assertNotNull(categoryDTO.getSubCategories(), "SubCategories list should not be null");
        assertEquals(1, categoryDTO.getSubCategories().size(), "SubCategories list size does not match");
        assertEquals(subCategory1.getId(), categoryDTO.getSubCategories().get(0).getId(), "SubCategory ID does not match");
        assertEquals(subCategory1.getName(), categoryDTO.getSubCategories().get(0).getName(), "SubCategory name does not match");
    }

    @Test
    void testSubCategoryListMapToSubCategoryListDTO() {

        SubCategory subCategory1 = SubCategory.builder().id(1L).name("SubCategory1").build();

        SubCategory subCategory2 = SubCategory.builder().id(2L).name("SubCategory2").build();

        List<SubCategory> subCategoryList = Arrays.asList(subCategory1, subCategory2);
        List<SubCategoryDTO> subCategoryDTOList = SubCategoryMapper.toListDTO(subCategoryList);

        assertNotNull(subCategoryDTOList, "The returned list should not be null");
        assertEquals(2, subCategoryDTOList.size(), "The size of the returned list does not match the expected value");

        SubCategoryDTO subCategoryDTO1 = subCategoryDTOList.get(0);
        assertEquals(subCategory1.getId(), subCategoryDTO1.getId(), "SubCategory ID does not match for the first subcategory");
        assertEquals(subCategory1.getName(), subCategoryDTO1.getName(), "SubCategory name does not match for the first subcategory");

        SubCategoryDTO subCategoryDTO2 = subCategoryDTOList.get(1);
        assertEquals(subCategory2.getId(), subCategoryDTO2.getId(), "SubCategory ID does not match for the second subcategory");
        assertEquals(subCategory2.getName(), subCategoryDTO2.getName(), "SubCategory name does not match for the second subcategory");
    }
}