package com.tutrit.sto.httpclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Order;
import com.tutrit.config.ConfigProvider;
import com.tutrit.gateway.OrderGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderGatewayHttp implements OrderGateway {

    private final ConfigProvider configProvider;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OrderGatewayHttp(final ConfigProvider configProvider,
                            final HttpClient httpClient,
                            final ObjectMapper objectMapper) {
        this.configProvider = configProvider;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Order> findAll() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(configProvider.getUrl() + "/orders"))
                .header("Content-Type", "application/json")
                .GET()
                .timeout(Duration.ofSeconds(1))
                .build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            List<Order> orderList = objectMapper.readValue(body, new TypeReference<List<Order>>() {});
            return orderList;

        } catch (HttpTimeoutException e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Order> findOrderById(final String orderId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(configProvider.getUrl() + "/orders/" + orderId))
                .header("Content-Type", "application/json")
                .GET()
                .timeout(Duration.ofSeconds(1))
                .build();
        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            Order order = objectMapper.readValue(body, Order.class);
            return Optional.of(order);

        } catch (HttpTimeoutException e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Order saveOrder(final Order order) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(configProvider.getUrl() + "/orders"))
                .POST(HttpRequest.BodyPublishers.ofString(makeBodyOrThrow(order)))
                .header("Content-Type", "application/json")
                .build();

        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            // TODO: 3/26/23 body has no content on save new instance. to fix
            Order savedOrder = objectMapper.readValue(body, Order.class);
            return savedOrder;

        } catch (HttpTimeoutException e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Order updateOrder(final Order order) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(configProvider.getUrl() + "/orders/" + order.orderId()))
                .PUT(HttpRequest.BodyPublishers.ofString(makeBodyOrThrow(order)))
                .header("Content-Type", "application/json")
                .build();

        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            Order savedOrder = objectMapper.readValue(body, Order.class);
            return savedOrder;

        } catch (HttpTimeoutException e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteOrderById(final String orderId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(configProvider.getUrl() + "/orders/" + orderId))
                .DELETE()
                .header("Content-Type", "application/json")
                .build();

        try {
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            return Boolean.getBoolean(body);
        } catch (HttpTimeoutException e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        }

        return false;
    }

    private String makeBodyOrThrow(Order order) {
        try {
            return objectMapper.writeValueAsString(order);
        } catch (Exception e) {
            // TODO: 3/26/23 add custom exception and notification for user and log;
            throw new RuntimeException("can't create body from oder", e);
        }
    }
}
