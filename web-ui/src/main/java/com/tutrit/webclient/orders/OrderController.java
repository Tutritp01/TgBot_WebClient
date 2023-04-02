package com.tutrit.webclient.orders;

import com.tutrit.bean.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    private ModelAndView openOrderTable() {
        var mov = makeModel();
        mov.setViewName("orders/orders-table");
        List<Order> orders = orderService.findAll();
        mov.addObject("orders", orders);
        return mov;
    }

    @GetMapping("/order")
    private ModelAndView openOrderFormForNewOrder() {
        var mov = makeModel();
        mov.setViewName("orders/order-form");
        var order = new Order(null, null);
        mov.addObject("order", order);
        return mov;
    }

    @PostMapping("/order/{id}")
    private String openOrderFormForNewOrder(@PathVariable String id,
                                            @RequestParam(name = "status", required = false) String status,
                                            @RequestParam Optional<String> save,
                                            @RequestParam Optional<String> delete) {
        save.ifPresent(s -> {
            switch (id) {
                case "null" -> handleNew(status);
                default -> handleUpdate(id, status);
            }});
        delete.ifPresent(d -> handleDelete(id));
        return "redirect:/orders";
    }

    @GetMapping("/order/{id}")
    private ModelAndView openOrderForm(@PathVariable String id) {
        var mov = makeModel();

        final Optional<Order> order = orderService.findOrderById(id);
        order.ifPresentOrElse(
                ord -> {
                    mov.addObject("order", ord);
                    mov.setViewName("orders/order-form");
                },
                () -> mov.setViewName("404"));
        return mov;
    }

    private void handleUpdate(final String id, final String status) {
        orderService.updateOrder(id, status);
    }

    private void handleNew(final String status) {
        orderService.createOrder(status);
    }

    private void handleDelete(final String id) {
        orderService.deleteOrderById(id);
    }

    private ModelAndView makeModel() {
        var mov = new ModelAndView();
        mov.addObject("uriPath", "/orders");
        return mov;
    }
}
