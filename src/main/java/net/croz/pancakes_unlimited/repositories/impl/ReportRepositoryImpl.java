package net.croz.pancakes_unlimited.repositories.impl;

import net.croz.pancakes_unlimited.repositories.ReportRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ReportRepositoryImpl implements ReportRepository
{
    @PersistenceContext
    private final EntityManager entityManager;

    public ReportRepositoryImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getMostOrderedIngredientsLast30Days()
    {
        Query query = entityManager.createNativeQuery("" +
                "SELECT ingredient.id AS id, ingredient.name AS name, ingredient.is_healthy AS isHealthy, COUNT(*) AS orderedTimes FROM ingredient\n"+
                "JOIN pancake_has_ingredient ON ingredient.id=pancake_has_ingredient.ingredient_id\n" +
                "JOIN pancake on pancake.id=pancake_has_ingredient.pancake_id\n" +
                "JOIN sales_order on sales_order.id=pancake.sales_order_id\n" +
                "WHERE sales_order.order_date >= NOW() - INTERVAL 30 DAY\n" +
                "GROUP BY ingredient.id ORDER BY orderedTimes DESC" +
                "");

        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> getMostHealthyOrderedIngredientsLast30Days()
    {
        Query query = entityManager.createNativeQuery("" +
                "SELECT ingredient.id AS id, ingredient.name AS name, ingredient.is_healthy AS isHealthy, COUNT(ingredient_id) AS orderedTimes FROM ingredient\n"+
                "JOIN pancake_has_ingredient ON ingredient.id=pancake_has_ingredient.ingredient_id\n" +
                "JOIN pancake on pancake.id=pancake_has_ingredient.pancake_id\n" +
                "JOIN sales_order on sales_order.id=pancake.sales_order_id\n" +
                "WHERE sales_order.order_date >= NOW() - INTERVAL 30 DAY AND ingredient.is_healthy=TRUE\n" +
                "GROUP BY ingredient.id ORDER BY orderedTimes DESC" +
                "");

        return query.getResultList();
    }


}
