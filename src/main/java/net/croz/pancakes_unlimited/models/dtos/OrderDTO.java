package net.croz.pancakes_unlimited.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.croz.pancakes_unlimited.models.responses.OrderedPancakeResp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO
{
    private Integer id;
    private Date orderDate;
    private String description;
    private String orderNumber;
    List<OrderedPancakeResp> orderedPancakes;
    //    private PriceResponse price;
    BigDecimal totalPrice;
    BigDecimal discount;
    BigDecimal totalPriceWithDiscount;
}
