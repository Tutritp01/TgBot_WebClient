package com.tutrit.webclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Engineer;
import com.tutrit.config.MySpringContext;
import com.tutrit.gateway.EngineerGateway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = MySpringContext.SpringConfig.class)
class EngineerControllerTest {

    @MockBean
    EngineerGateway engineerGateway;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findEngineerById() throws Exception{
        when(engineerGateway.findEngineerById("id1")).thenReturn(makeEngineer());

        final MvcResult result = mockMvc.perform(get("/engineers/id1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("engineer", Matchers.equalTo(makeEngineer())))
                .andExpect(view().name("makeEngineer-form"))
                .andReturn();

        Engineer actualEngineer = (Engineer) Objects.requireNonNull(result.getModelAndView()).getModel().get("engineer");
        assertEquals(makeEngineer(), actualEngineer);
    }

    @Test
    void saveEngineer() throws Exception {
        when(engineerGateway.saveEngineer(new Engineer("id1", "firstName1", "lastName1", "function1", "category1", "education1", 3, 10))).thenReturn(makeEngineer());

        mockMvc.perform(post("/engineers/id1")
                        .param("engineerId", "id1")
                        .param("firstName", "firstName1")
                        .param("lastName", "lastName1")
                        .param("function", "function1")
                        .param("category", "category1")
                        .param("education", "education1")
                        .param("experience", "3")
                        .param("generalExperience", "10")
                        .param("save", "push"))
                .andExpect(view().name("redirect:/engineers/id1"))
                .andExpect(status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(engineerGateway).saveEngineer(makeEngineer());
    }

    private Engineer makeEngineer() {
        return new Engineer("id1", "firstName1", "lastName1", "function1", "category1", "education1", 3, 10);
    }
}