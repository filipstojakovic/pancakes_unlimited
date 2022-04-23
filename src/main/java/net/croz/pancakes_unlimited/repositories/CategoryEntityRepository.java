package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, String>
{
}