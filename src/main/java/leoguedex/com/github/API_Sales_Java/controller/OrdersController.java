package leoguedex.com.github.API_Sales_Java.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import leoguedex.com.github.API_Sales_Java.model.OrderedItem;
import leoguedex.com.github.API_Sales_Java.model.Orders;
import leoguedex.com.github.API_Sales_Java.model.dto.InformationItemOrderDto;
import leoguedex.com.github.API_Sales_Java.model.dto.InformationOrderDto;
import leoguedex.com.github.API_Sales_Java.model.dto.OrdersDto;
import leoguedex.com.github.API_Sales_Java.model.dto.UpdateStatusOrderDto;
import leoguedex.com.github.API_Sales_Java.model.enums.StatusOrder;
import leoguedex.com.github.API_Sales_Java.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "Orders API")
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping
    @ApiOperation(value = "Insert a new Order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order with successfully"),
            @ApiResponse(code = 400, message = "Error Validation"),
            @ApiResponse(code = 403, message = "User without access")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Integer includeOrder(@RequestBody @Valid OrdersDto ordersDto){
        Orders orders = ordersService.includeOrder(ordersDto);
        return orders.getId();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Show data about new Order")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Order found successfully"),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public InformationOrderDto showOrder(@PathVariable Integer id){
        return ordersService.showOrder(id)
                .map(orders ->  builderInformacaoPedidoDto(orders))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found"));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing order ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Order found successfully"),
            @ApiResponse(code = 400, message = "Order not found"),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 405, message = "Invalid Status Option")
    })
    @ResponseStatus(HttpStatus.OK)
    private void updateStatus(@PathVariable Integer id, @RequestBody UpdateStatusOrderDto updateStatusOrderDto){
        String newStatus = updateStatusOrderDto.getNewStatus();
        ordersService.updateStatus(id, StatusOrder.valueOf(newStatus));
    }

    private InformationOrderDto builderInformacaoPedidoDto(Orders orders) {
        String dataPedido = orders.getDateOrder().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return InformationOrderDto.builder()
                .code(orders.getId())
                .orderDate(dataPedido)
                .cpf(orders.getClient().getCpf())
                .clientName(orders.getClient().getName())
                .total(orders.getTotal())
                .status(orders.getStatusOrder().name())
                .items(builderInformacaoItemPedidoDto(orders.getItems()))
                .build();
    }

    private List<InformationItemOrderDto> builderInformacaoItemPedidoDto (List<OrderedItem> itens){
       return itens.stream()
                .map(item-> InformationItemOrderDto.builder()
                    .productDescription(item.getProduct().getDescription())
                    .unitPrice(item.getProduct().getPrice())
                    .amount(item.getAmount())
                    .build()
                ).collect(Collectors.toList());
    }

}
