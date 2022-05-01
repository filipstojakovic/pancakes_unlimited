package net.croz.pancakes_unlimited.services.impl;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IngredientServiceImplTest
{

//    @Test
//    void mergeSamePancakesUpdateQuantity()
//    {
//        List<PancakeRequest> notMergedList = List.of(new PancakeRequest(1, 10),
//                new PancakeRequest(2, 23),
//                new PancakeRequest(1, 30),
//                new PancakeRequest(3, 45)
//        );
//
//        var mergedList = notMergedList.stream()
//                .collect(
//                        Collectors.groupingBy(PancakeRequest::getPancakeId,
//                        Collectors.reducing(this::mergePancakes)))
//                .values().stream()
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
//
//
//        assertThat(mergedList.size()).isEqualTo(3);
//        int pancakeId = 1;
//        PancakeRequest mergedPancake = mergedList.stream().filter(x->x.getPancakeId().equals(pancakeId)).findFirst().get();
//        assertThat(mergedPancake.getPancakeId()).isEqualTo(pancakeId);
//        assertThat(mergedPancake.getQuantity()).isEqualTo(40);
//
//    }
//
//    private PancakeRequest mergePancakes(PancakeRequest x, PancakeRequest y)
//    {
//        return new PancakeRequest(x.getPancakeId(), x.getQuantity() + y.getQuantity());
//    }

}