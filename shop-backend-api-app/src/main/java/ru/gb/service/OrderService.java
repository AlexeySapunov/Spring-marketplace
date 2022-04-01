package ru.gb.service;

import ru.gb.controller.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> findAll();

    void createOrder();
}
