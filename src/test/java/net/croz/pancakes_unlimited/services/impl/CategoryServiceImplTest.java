package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.BadRequestException;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.repositories.CategoryEntityRepository;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest
{
    @Mock
    private CategoryEntityRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp()
    {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void findAllCategories()
    {
        categoryService.findAll();
        verify(categoryRepository).findAll();
    }

    @Test
    void findCategoryById()
    {
        int id = 1;
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(1);
        categoryEntity.setName("baza");
        given(categoryRepository.findById(id)).willReturn(Optional.of(categoryEntity));

        CategoryEntity returnedValue = categoryService.findById(id);

        assertThat(returnedValue).isEqualTo(categoryEntity);
    }

    @Test
    void findCategoryByIdNotFoundException()
    {
        int id = 1;
        given(categoryRepository.findById(id)).willReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.findById(id))
                .isInstanceOf(NotFoundException.class);
    }
}