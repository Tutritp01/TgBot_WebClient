package com.tutrit.webclient.controller;

import com.tutrit.bean.Engineer;
import com.tutrit.gateway.EngineerGateway;
import com.tutrit.webclient.config.SpringContextConfig;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringContextConfig.SpringConfig.class)
class EngineerControllerTest {

    @MockBean
    EngineerGateway engineerGateway;
    @Autowired
    MockMvc mockMvc;


    @Test
    void findEngineerById() throws Exception {
        when(engineerGateway.findEngineerById("id1")).thenReturn(Optional.of(createEngineer()));

        final MvcResult result = mockMvc.perform(get("/engineers/id1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("engineer", Matchers.equalTo(createEngineer())))
                .andExpect(view().name("engineer-form"))
                .andReturn();

        Engineer actualEngineer = (Engineer) Objects.requireNonNull(result.getModelAndView()).getModel().get("engineer");
        assertEquals(createEngineer(), actualEngineer);
    }

    @Test
    void findUserByIdIfUserNull() throws Exception {
        when(engineerGateway.findEngineerById("222"))
                .thenReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/engineers/222"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("engineer", Matchers.equalTo(engineerHasNullOfFields())))
                .andExpect(model().attribute("error_404", Matchers.equalTo("Engineer not found")))
                .andExpect(view().name("engineer-form"))
                .andReturn();
        Engineer actualUser = (Engineer) Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("engineer");
        assertEquals(engineerHasNullOfFields(), actualUser);
    }

    @Test
    void saveEngineer() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/engineers/52")
                        .param("engineerId", "52")
                        .param("firstName", "firstName1")
                        .param("lastName", "lastName1")
                        .param("function", "function1")
                        .param("category", "category1")
                        .param("education", "education1")
                        .param("experience", "3")
                        .param("generalExperience", "10")
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
                .perform(MockMvcRequestBuilders
                        .post("/engineers/52?")
                        .param("engineerId", "52")
                        .param("firstName", "firstName1")
                        .param("lastName", "lastName1")
                        .param("function", "function1")
                        .param("category", "category1")
                        .param("education", "education1")
                        .param("experience", "3")
                        .param("generalExperience", "10")
                        .param("delete", "push"))
                .andExpect(view().name("redirect:/engineers/52"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Mockito.verify(engineerGateway).deleteEngineerById("52");
    }

    private Engineer createEngineer() {
        return new Engineer("id1", "firstName1", "lastName1", "function1", "category1", "education1", 3, 10);
    }

    private Engineer verifyEngineer() {
        return new Engineer("52", "firstName1", "lastName1", "function1", "category1", "education1", 3, 10);
    }

    private Engineer engineerHasNullOfFields() {
        return new Engineer(null, null, null, null, null, null, null, null);
    }
}
