package leoguedex.com.github.API_Sales_Java.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationOrderDto {

    private Integer code;
    private String cpf;
    private String clientName;
    private BigDecimal total;
    private String orderDate;
    private String status;
    private List<InformationItemOrderDto> items;
}