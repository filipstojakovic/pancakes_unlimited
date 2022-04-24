package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PancakeEntityRepository extends JpaRepository<PancakeEntity,Integer>
{
}
