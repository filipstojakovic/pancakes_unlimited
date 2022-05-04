package net.croz.pancakes_unlimited.models.responses;

import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedPancakeResp
{
    private Integer pancakeId;
    private BigDecimal price;
}
