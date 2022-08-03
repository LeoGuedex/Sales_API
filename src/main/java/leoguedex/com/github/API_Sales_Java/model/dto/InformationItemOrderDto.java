package leoguedex.com.github.API_Sales_Java.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationItemOrderDto {

    private String productDescription;
    private BigDecimal unitPrice;
    private Integer amount;
}
