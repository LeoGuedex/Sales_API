package leoguedex.com.github.API_Sales_Java.service;

import leoguedex.com.github.API_Sales_Java.exception.BusinessRulesException;
import leoguedex.com.github.API_Sales_Java.exception.OrderNotFoundException;
import leoguedex.com.github.API_Sales_Java.model.Client;
import leoguedex.com.github.API_Sales_Java.model.OrderedItem;
import leoguedex.com.github.API_Sales_Java.model.Orders;
import leoguedex.com.github.API_Sales_Java.model.Product;
import leoguedex.com.github.API_Sales_Java.model.dto.OrdersDto;
import leoguedex.com.github.API_Sales_Java.model.enums.StatusOrder;
import leoguedex.com.github.API_Sales_Java.repository.ClientRepository;
import leoguedex.com.github.API_Sales_Java.repository.OrderedItemRepository;
import leoguedex.com.github.API_Sales_Java.repository.OrdersRepository;
import leoguedex.com.github.API_Sales_Java.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private OrdersRepository ordersRepository;

    private final OrderedItemRepository orderedItemRepository;

    private final ClientRepository clientRepository;

    private final ProductRepository productRepository;

    public OrdersService(OrdersRepository ordersRepository, OrderedItemRepository orderedItemRepository,
                         ClientRepository clientRepository, ProductRepository productRepository) {
        this.ordersRepository = ordersRepository;
        this.orderedItemRepository = orderedItemRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public Orders includeOrder(OrdersDto ordersDto) {
        validItems(ordersDto);
        Client cliente = findClient(ordersDto);
        Orders pedido = builderOrder(ordersDto, cliente);

        List<OrderedItem> itens = builderItemOrder(ordersDto, pedido);
        ordersRepository.save(pedido);
        orderedItemRepository.saveAll(itens);

        return pedido;
    }

    public Optional<Orders> showOrder(Integer id) {
        return ordersRepository.findByIdFetchItems(id);
    }

    @Transactional
    public void updateStatus(Integer id, StatusOrder statusOrder) {
        ordersRepository.findById(id)
                .map(orders -> {
                    orders.setStatusOrder(statusOrder);
                    return ordersRepository.save(orders);
                }).orElseThrow(() -> new OrderNotFoundException("Order not found."));
    }

    private List<OrderedItem> builderItemOrder(OrdersDto ordersDto, Orders orders) {
        return ordersDto.getItems().stream()
                .map(itemsOrdersDto -> {
                    Product produto = productRepository.findById(itemsOrdersDto.getProduct())
                            .orElseThrow(() -> new BusinessRulesException("Código de Cliente inválido"));

                    return OrderedItem.builder()
                            .orders(orders)
                            .product(produto)
                            .amount(itemsOrdersDto.getAmount())
                            .build();
                }).collect(Collectors.toList());
    }

    private void validItems(OrdersDto ordersDto) {
        if (ordersDto.getItems().isEmpty()) {
            throw new BusinessRulesException("It is not possible to place an order without items.");
        }
    }

    private Orders builderOrder(OrdersDto pedidoDto, Client client) {
        return Orders.builder()
                .client(client)
                .dateOrder(LocalDate.now())
                .total(pedidoDto.getTotal())
                .statusOrder(StatusOrder.PENDING)
                .build();
    }

    private Client findClient(OrdersDto ordersDto) {
        return clientRepository.findById(ordersDto.getClient())
                .orElseThrow(() -> new BusinessRulesException("Client code invalid"));
    }

}
