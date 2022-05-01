package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.models.entities.SalesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderEntityRepository extends JpaRepository<SalesOrderEntity, Integer>
{
}
