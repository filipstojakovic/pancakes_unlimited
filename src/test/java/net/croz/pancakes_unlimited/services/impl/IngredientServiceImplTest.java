package net.croz.pancakes_unlimited.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.croz.pancakes_unlimited.PancakesUnlimitedApplication;
import net.croz.pancakes_unlimited.models.entities.PancakeEntity;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.repositories.IngredientEntityRepository;
import net.croz.pancakes_unlimited.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IngredientServiceImplTest
{

    @Test
    void mergeSamePancakesUpdateQuantity()
    {
        List<PancakeRequest> notMergedList = List.of(new PancakeRequest(1, 10),
                new PancakeRequest(2, 23),
                new PancakeRequest(1, 30),
                new PancakeRequest(3, 45)
        );

        var mergedList = notMergedList.stream()
                .collect(
                        Collectors.groupingBy(PancakeRequest::getPancakeId,
                        Collectors.reducing(this::mergePancakes)))
                .values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());


        assertThat(mergedList.size()).isEqualTo(3);
        int pancakeId = 1;
        PancakeRequest mergedPancake = mergedList.stream().filter(x->x.getPancakeId().equals(pancakeId)).findFirst().get();
        assertThat(mergedPancake.getPancakeId()).isEqualTo(pancakeId);
        assertThat(mergedPancake.getQuantity()).isEqualTo(40);

    }

    private PancakeRequest mergePancakes(PancakeRequest x, PancakeRequest y)
    {
        return new PancakeRequest(x.getPancakeId(), x.getQuantity() + y.getQuantity());
    }

}