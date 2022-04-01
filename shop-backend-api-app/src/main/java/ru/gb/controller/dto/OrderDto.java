package ru.gb.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private Long id;

    private LocalDateTime orderDate;

    private String username;

    private List<OrderLineItemDto> orderLineItems;

    public OrderDto() {
    }

    public OrderDto(Long id, LocalDateTime orderDate, String username, List<OrderLineItemDto> orderLineItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.username = username;
        this.orderLineItems = orderLineItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItemDto> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }
}
