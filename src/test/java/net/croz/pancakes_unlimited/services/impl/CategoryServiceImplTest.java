//package net.croz.pancakes_unlimited.services.impl;
//
//import net.croz.pancakes_unlimited.exceptions.NotFoundException;
//import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
//import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
//import net.croz.pancakes_unlimited.repositories.CategoryEntityRepository;
//import net.croz.pancakes_unlimited.services.CategoryService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ExtendWith(MockitoExtension.class)
//class CategoryServiceImplTest
//{
//    @Mock
//    private CategoryEntityRepository categoryRepository;
//    private CategoryService categoryService;
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @BeforeEach
//    void setUp()
//    {
//        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
//    }
//
//    @Test
//    void findAllCategories()
//    {
//        categoryService.findAll();
//        verify(categoryRepository).findAll();
//    }
//
//    @Test
//    void findCategoryById()
//    {
//        int id = 1;
//        CategoryEntity categoryEntity = new CategoryEntity();
//        categoryEntity.setCategoryId(1);
//        categoryEntity.setName("baza");
//        given(categoryRepository.findById(id)).willReturn(Optional.of(categoryEntity));
//
//        CategoryDTO returnedValue = categoryService.findById(id);
//
//        assertThat(returnedValue).isEqualTo(categoryEntity);
//    }
//
//    @Test
//    void findCategoryByIdNotFoundException()
//    {
//        int id = 1;
//        given(categoryRepository.findById(id)).willReturn(Optional.empty());
//        assertThatThrownBy(() -> categoryService.findById(id))
//                .isInstanceOf(NotFoundException.class);
//    }
//}