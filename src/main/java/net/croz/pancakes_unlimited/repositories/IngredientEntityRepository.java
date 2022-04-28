package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.models.entities.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientEntityRepository extends JpaRepository<IngredientEntity, Integer>
{
    Optional<IngredientEntity> findByName(String ingredientName);
}