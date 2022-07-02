package com.javovka.userservice.service;

import com.javovka.userservice.dto.OrderDto;
import com.javovka.userservice.models.Orders;

import java.util.List;

public interface OrdersService {

    List<Orders> getAllOrders();
    Orders saveOrder(OrderDto order, String header);

}
