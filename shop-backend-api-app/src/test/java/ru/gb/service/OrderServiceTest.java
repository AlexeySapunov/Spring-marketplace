package ru.gb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.gb.controller.dto.OrderDto;
import ru.gb.persist.OrderRepository;
import ru.gb.persist.ProductRepository;
import ru.gb.persist.UserRepository;
import ru.gb.persist.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    private OrderService orderService;

    private OrderRepository orderRepository;

    private CartService cartService;

    private ProductRepository  productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        orderRepository = mock(OrderRepository.class);
        SimpMessagingTemplate template = mock(SimpMessagingTemplate.class);
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
        cartService = mock(CartService.class);
        orderService = new OrderServiceImpl(cartService, userRepository,
                orderRepository, productRepository, template, rabbitTemplate);
    }

    @Test
    public void findOrdersByUsernameTest() {
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("Username");

        orderService.createOrder(expectedUser.getUsername());

        when(orderRepository.findAllByUsername(eq(expectedUser.getUsername())))
                .thenReturn(List.of(expectedUser.getOrders().toArray(new Order[0])));

        List<OrderDto> lst = orderService.findOrdersByUsername(expectedUser.getUsername());

        assertFalse(lst.isEmpty());
        assertEquals(expectedUser.getId(), lst.get(0).getId());
        assertEquals(expectedUser.getUsername(), lst.get(0).getUsername());
    }

    @Test
    public void testCreateOrder() {
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("Username");

        Order expectedOrder = orderRepository.save(
                new Order(
                        1L,
                        LocalDateTime.now(),
                        Order.OrderStatus.CREATED,
                        expectedUser
                )
        );

        List<OrderLineItem> lineItems = cartService.getLineItems()
                .stream()
                .map(lineItem -> new OrderLineItem(
                        1L,
                        expectedOrder,
                        findProductById(lineItem.getProductId()),
                        lineItem.getProductDto().getPrice(),
                        lineItem.getQty(),
                        lineItem.getColor(),
                        lineItem.getMaterial())
                ).collect(Collectors.toList());

        expectedOrder.setOrderLineItems(lineItems);
        orderRepository.save(expectedOrder);

        List<OrderDto> lst = orderService.findOrdersByUsername(expectedUser.getUsername());

        assertFalse(lst.isEmpty());
        assertEquals(expectedOrder.getId(), lst.get(0).getId());
    }

    private Product findProductById(Long id) {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName("Category name");

        Brand expectedBrand = new Brand();
        expectedBrand.setId(1L);
        expectedBrand.setName("Brand name");

        Product expectedProduct = new Product();
        expectedProduct.setId(id);
        expectedProduct.setName("Product name");
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setBrand(expectedBrand);
        expectedProduct.setPictures(new ArrayList<>());
        expectedProduct.setPrice(new BigDecimal(12345));

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with this id"));
    }
}
