package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.models.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer>
{
    Optional<OrderEntity> findOrderEntityByOrderNumber(String orderNumber);
}
