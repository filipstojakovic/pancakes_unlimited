package net.croz.pancakes_unlimited.repositories;

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
        order.setOrderNumber("f71508c8-09a9-4a37-aaaa-64645ee62828");
        order.setDescription("cool description");
        order.setOrderDate(new Date(12345678));

        OrderEntity savedOrder = orderRepository.save(order);

        assertThat(savedOrder).isNotEqualTo(order);
    }
}