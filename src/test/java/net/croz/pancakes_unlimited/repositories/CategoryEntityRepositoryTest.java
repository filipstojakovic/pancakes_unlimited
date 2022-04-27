package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryEntityRepositoryTest
{
    @Autowired
    private CategoryEntityRepository categoryRepository;

    @Test
    void findCategoryByNameTest()
    {
        CategoryEntity categoryEntity = categoryRepository.findByName("fil").orElseThrow(NotFoundException::new);
        assertThat(categoryEntity.getName()).isEqualTo("fil");

    }


    @Test
    void findCategoryByNameThatDoesntExistTest()
    {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByName("unknown");
        assertThat(categoryEntity.get()).isEqualTo(null);
    }
}