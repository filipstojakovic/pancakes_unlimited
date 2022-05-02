package net.croz.pancakes_unlimited.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository
{
    List<Object[]> getMostOrderedIngredientsLast30Days();

    List<Object[]> getMostHealthyOrderedIngredientsLast30Days();
}
