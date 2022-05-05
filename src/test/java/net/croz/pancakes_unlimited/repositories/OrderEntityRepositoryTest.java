package net.croz.pancakes_unlimited.repositories;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.entities.OrderEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderEntityRepositoryTest
{
    @Autowired
    private OrderEntityRepository orderRepository;

    @BeforeEach
    void setUp()
    {
    }

    @AfterEach
    void tearDown()
    {
        orderRepository.deleteAll();
    }

    @Test
    void findOrderEntityByOrderNumberTest()
    {
        OrderEntity order = new OrderEntity();
        order.setId(169);
        String orderNumber = UUID.randomUUID().toString();
        order.setOrderNumber(orderNumber);
        order.setDescription("cool description");
        order.setOrderDate(new Date());
        orderRepository.save(order);

        OrderEntity OrderByOrderNumber = orderRepository.findOrderEntityByOrderNumber(orderNumber).orElseThrow(NotFoundException::new);

        assertThat(OrderByOrderNumber).isEqualTo(OrderByOrderNumber);
    }
}