package com.tutrit.gateway;

import com.tutrit.bean.Order;
import com.tutrit.bean.User;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    List<Order> findAll();

    Optional<Order> findOrderById(String orderId);

    Order saveOrder(Order order);

    Order updateOrder(Order order);

    boolean deleteOrderById(String orderId);
}
