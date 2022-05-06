package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.PancakesUnlimitedApplication;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.repositories.CategoryEntityRepository;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PancakesUnlimitedApplication.class)
class CategoryServiceImplTest
{
    @Mock
    private CategoryEntityRepository categoryRepository;
    @InjectMocks
    private ModelMapper modelMapper;
    private CategoryService categoryService;

    @BeforeEach
    public void setUp()
    {
        categoryService = new CategoryServiceImpl(categoryRepository,modelMapper);
    }

    @Test
    void findAll_returnResult()
    {
        CategoryEntity categoryEntity1 = new CategoryEntity(1,"baza");
        CategoryEntity categoryEntity2 = new CategoryEntity(2,"fil");
        when(categoryRepository.findAll()).thenReturn(List.of(categoryEntity1,categoryEntity2));

        var resultDTO = categoryService.findAll();

        List<CategoryDTO> expectedResult = List.of(new CategoryDTO(1,"baza"), new CategoryDTO(2,"fil"));

        assertThat(resultDTO).isEqualTo(expectedResult);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void findById_returnResult()
    {
        int id = 1;
        CategoryEntity categoryEntity = new CategoryEntity(1,"baza");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(categoryEntity));

        var returnedValue = categoryService.findById(id);
        verify(categoryRepository).findById(id);

        CategoryDTO expectedResult = new CategoryDTO(1,"baza");
        assertThat(returnedValue).isEqualTo(expectedResult);
    }

    @Test
    void findById_throwNotFoundException()
    {
        int id = -1;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.findById(id))
                        .isInstanceOf(NotFoundException.class);
    }

}