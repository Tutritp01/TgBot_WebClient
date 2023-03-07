package com.tutrit.httpclient.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

import static java.net.http.HttpClient.Version.HTTP_2;

@Configuration
public class HttpClientConfig {
    @Value("${url.rest-api}")
    private  String url;

   /* private final String url;
    public HttpClientConfig(@Value("${url.rest-api}")final String url) {
        this.url = url;
    }*/
   @Bean
   public ObjectMapper objectMapper(){
       final ObjectMapper om = new ObjectMapper();
       //Don't fail if additional fields in incoming JSON, just ignore
       om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
       //Don't fail on incoming JSON missing fields
       om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
       om.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
       //Don't include null fields into json (need for patch methods tests)
       om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
       om.registerModule(new JavaTimeModule());
       om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
       return om;
   }

    @Bean
    public HttpClient httpClient(){
        return HttpClient.newBuilder().version(HTTP_2).build();
    }

    public String getUrl(){
        return url;
    }

}
