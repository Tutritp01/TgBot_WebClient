package com.tutrit.httpclient.gateway.CustomerGateway;

import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpCustomerGatewayTest {

    @Test
    void saveCustomer() {

    }

    /* data as form*/
    Map<String, String> formData = new HashMap<>();
//        formData.put("name", "");
//        formData.put("customerId", "");
//        formData.put("city", "null");
//        formData.put("phoneNumber", "");
//        formData.put("email", "");

/*create JSON of form*/
    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }

    @Test
    void findCustomerById() {
    }

    @Test
    void deleteCustomerById() {
    }
}