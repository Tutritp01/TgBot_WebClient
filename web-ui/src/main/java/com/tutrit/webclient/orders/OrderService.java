package com.tutrit.webclient.orders;

import com.tutrit.bean.Order;
import com.tutrit.gateway.OrderGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired(required = false)
    OrderGateway orderGateway;

    List<Order> findAll() {
        return orderGateway.findAll();
    }

    Optional<Order> findOrderById(String orderId) {
        return orderGateway.findOrderById(orderId);
    }

    Order createOrder(String status) {
        return orderGateway.saveOrder(new Order(null, status));
    }

    Order updateOrder(String id, String status) {
        return orderGateway.updateOrder(new Order(id, status));
    }

    boolean deleteOrderById(String orderId) {
        return orderGateway.deleteOrderById(orderId);
    }
}
