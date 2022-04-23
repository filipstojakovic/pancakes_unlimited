package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.models.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientEntityRepository extends JpaRepository<IngredientEntity, Integer>
{
}