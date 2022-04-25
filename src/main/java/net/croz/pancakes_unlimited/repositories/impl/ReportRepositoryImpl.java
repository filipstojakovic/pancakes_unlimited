package net.croz.pancakes_unlimited.repositories.impl;

import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.repositories.ReportRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public List<CategoryEntity> getCategories()
    {
        return (List<CategoryEntity>)entityManager.createNativeQuery("SELECT * FROM category").getResultList();
    }

//    public List<?> queryForMovies() {
//        EntityManager em = getEntityManager();
//        List<?> movies = em.createQuery("SELECT movie from Movie movie where movie.language = ?1")
//                .setParameter(1, "English")
//                .getResultList();
//        return movies;
//    }

}
