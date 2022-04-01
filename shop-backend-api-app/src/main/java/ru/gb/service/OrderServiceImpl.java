package ru.gb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.controller.dto.OrderDto;
import ru.gb.controller.dto.OrderLineItemDto;
import ru.gb.persist.OrderRepository;
import ru.gb.persist.ProductRepository;
import ru.gb.persist.model.Order;
import ru.gb.persist.model.OrderLineItem;
import ru.gb.persist.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final CartService cartService;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(CartService cartService, OrderRepository orderRepository, ProductRepository productRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getOrderDate(),
                        order.getUser().getUsername(),
                        order.getOrderLineItems().stream()
                                .map(orderLineItem -> new OrderLineItemDto(
                                        orderLineItem.getId(),
                                        orderLineItem.getOrder().getId(),
                                        orderLineItem.getProduct().getId(),
                                        orderLineItem.getProduct().getName(),
                                        orderLineItem.getPrice(),
                                        orderLineItem.getQty(),
                                        orderLineItem.getColor(),
                                        orderLineItem.getMaterial()
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @Override
    public void createOrder() {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Cart is empty");
            return;
        }

        Order order = orderRepository.save(
                new Order(
                        null,
                        LocalDateTime.now()
                )
        );

        List<OrderLineItem> lineItems = cartService.getLineItems()
                .stream()
                .map(lineItem -> new OrderLineItem(
                        null,
                        order,
                        findProductById(lineItem.getProductId()),
                        lineItem.getProductDto().getPrice(),
                        lineItem.getQty(),
                        lineItem.getColor(),
                        lineItem.getMaterial())
                ).collect(Collectors.toList());

        order.setOrderLineItems(lineItems);
        orderRepository.save(order);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with this id"));
    }
}
