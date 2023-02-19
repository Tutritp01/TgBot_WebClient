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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(view().name("engineer-form"))
                .andReturn();

        Engineer actualEngineer = (Engineer) Objects.requireNonNull(result.getModelAndView()).getModel().get("engineer");
        assertEquals(makeEngineer(), actualEngineer);
    }

    @Test
    void saveEngineer() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/engineers/52?" +
                                "engineerId=52" +
                                "&firstName=firstName1" +
                                "&lastName=lastName1" +
                                "&function=function1" +
                                "&category=category1" +
                                "&education=education1" +
                                "&experience=3" +
                                "&generalExperience=10")
                        .param("save", "push"))
                .andExpect(view().name("redirect:/engineers/52"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Mockito.verify(engineerGateway).saveEngineer(verifyEngineer());
    }

    @Test
    void deleteEngineer() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/engineers/52?" +
                                "engineerId=52" +
                                "&firstName=firstName1" +
                                "&lastName=lastName1" +
                                "&function=function1" +
                                "&category=category1" +
                                "&education=education1" +
                                "&experience=3" +
                                "&generalExperience=10")
                        .param("delete", "push"))
                .andExpect(view().name("redirect:/engineers/52"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Mockito.verify(engineerGateway).deleteEngineerById("52");
    }

    private Engineer makeEngineer() {
        return new Engineer("id1", "firstName1", "lastName1", "function1", "category1", "education1", 3, 10);
    }

    private Engineer verifyEngineer() {
        return new Engineer("52", "firstName1", "lastName1", "function1", "category1", "education1", 3, 10);
    }
}