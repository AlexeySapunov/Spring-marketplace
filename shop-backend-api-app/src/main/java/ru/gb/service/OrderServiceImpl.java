package ru.gb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.gb.controller.dto.OrderDto;
import ru.gb.controller.dto.OrderLineItemDto;
import ru.gb.persist.OrderRepository;
import ru.gb.persist.ProductRepository;
import ru.gb.persist.UserRepository;
import ru.gb.persist.model.Order;
import ru.gb.persist.model.OrderLineItem;
import ru.gb.persist.model.Product;
import ru.gb.persist.model.User;
import ru.gb.service.dto.OrderStatus;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final CartService cartService;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final SimpMessagingTemplate template;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderServiceImpl(CartService cartService, UserRepository userRepository,
                            OrderRepository orderRepository, ProductRepository productRepository,
                            SimpMessagingTemplate template, RabbitTemplate rabbitTemplate) {
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.template = template;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<OrderDto> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username).stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getOrderDate(),
                        order.getStatus().name(),
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

    @Transactional
    public void createOrder(String username) {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Cart is empty");
            return;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.save(
                new Order(
                        null,
                        LocalDateTime.now(),
                        Order.OrderStatus.CREATED,
                        user)
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
        cartService.clear();
        rabbitTemplate.convertAndSend("order.exchange", "new_order",
                new OrderStatus(order.getId(), order.getStatus().toString()));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with this id"));
    }

    @RabbitListener(queues = "processed.order.queue")
    public void receiver(OrderStatus orderStatus) {
        logger.info("New order status received id = {}, status = {}",
                orderStatus.getOrderId(), orderStatus.getStatus());

        template.convertAndSend("/order_out/order", orderStatus);
    }
}
