package net.croz.pancakes_unlimited.utils;

import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.entities.*;
import net.croz.pancakes_unlimited.models.responses.IngredientReportResponse;
import net.croz.pancakes_unlimited.models.responses.PancakeIngredientResponse;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class MapUtils
{
    private static final int ID_POSITION = 0;
    private static final int NAME_POSITION = 1;
    private static final int IS_HEALTHY_POSITION = 2;
    private static final int ORDERED_TIMES_POSITION = 3;

    /**
     * Mapping (converting) sql result list of object array into list of IngredientReportResponse
     *
     * @param sqlReportResult list of object arrays that are result of sql query
     * @return nicely packed result
     */
    public static List<IngredientReportResponse> mapToIngredientReport(List<Object[]> sqlReportResult)
    {
        return sqlReportResult.stream().map(result ->
            {
                IngredientReportResponse response = new IngredientReportResponse();
                response.setIngredientId((Integer) result[ID_POSITION]);
                response.setName((String) result[NAME_POSITION]);
                response.setIsHealthy((Boolean) result[IS_HEALTHY_POSITION]);
                response.setOrderedTimes(((BigInteger) result[ORDERED_TIMES_POSITION]));
                return response;
            }).collect(Collectors.toList());
    }


    public static PancakeDTO mapPancakeEntityToPancakeDTO(PancakeEntity pancake, ModelMapper modelMapper)
    {
        List<PancakeIngredientResponse> pancakeIngredientResponses =
                mapPancakeHasIngredientToPancakeIngredientResponse(pancake.getPancakeIngredients(), modelMapper);

        PancakeDTO pancakeDTO = new PancakeDTO();
        pancakeDTO.setId(pancake.getId());
        pancakeDTO.setPancakeIngredients(pancakeIngredientResponses);
        return pancakeDTO;
    }

    public static List<PancakeIngredientResponse> mapPancakeHasIngredientToPancakeIngredientResponse(List<PancakeHasIngredient> pancakeHasIngredients, ModelMapper modelMapper)
    {
        return pancakeHasIngredients.stream()
                .map(pancakeHasIngredient ->
                    {
                        IngredientEntity ingredientEntity = pancakeHasIngredient.getIngredient();
                        BigDecimal price = pancakeHasIngredient.getPrice();
                        PancakeIngredientResponse pancakeIngredientResponse = new PancakeIngredientResponse();
                        pancakeIngredientResponse.setIngredientId(ingredientEntity.getIngredientId());
                        pancakeIngredientResponse.setIngredientName(ingredientEntity.getName());
                        pancakeIngredientResponse.setPrice(price);

                        CategoryEntity categoryEntity = ingredientEntity.getIngredientCategory();
                        pancakeIngredientResponse.setIngredientCategory(modelMapper.map(categoryEntity, CategoryDTO.class));

                        return pancakeIngredientResponse;
                    })
                .collect(Collectors.toList());
    }

    public static List<PancakeHasIngredient> createPancakeHasIngredients(List<IngredientEntity> ingredientEntityList, PancakeEntity pancake)
    {
        return ingredientEntityList.stream().map(ingredientEntity ->
            {
                PancakeHasIngredient pancakeHasIngredient = new PancakeHasIngredient();
                pancakeHasIngredient.setPancake(pancake);
                pancakeHasIngredient.setIngredient(ingredientEntity);
                pancakeHasIngredient.setPrice(ingredientEntity.getPrice());
                return pancakeHasIngredient;
            }).collect(Collectors.toList());
    }

    public static OrderDTO mapToOrderDTO(OrderEntity orderEntity, ModelMapper modelMapper)
    {
        OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
        List<Integer> pancakeIds = orderEntity.getPancakeEntityList().stream().map(PancakeEntity::getId).collect(Collectors.toList());
        orderDTO.setPancakeIds(pancakeIds);
        return orderDTO;
    }
}
