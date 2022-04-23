package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Integer>
{
    Optional<CategoryEntity> findByName(String name);
}