package com.tutrit.webclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.webclient.config.SpringContextConfig;
import com.tutrit.gateway.UserGateway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringContextConfig.SpringConfig.class)
class UserControllerTests {
    @MockBean
    UserGateway userGateway;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findUserById() throws Exception {
        when(userGateway.findUserById("666"))
                .thenReturn(Optional.of(createUser()));
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/users/666"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("user", Matchers.equalTo(createUser())))
                .andExpect(view().name("user-form"))
                .andReturn();
        User actualUser = (User) Objects.requireNonNull(result.getModelAndView()).getModel().get("user");
        assertEquals(createUser(), actualUser);
    }

    @Test
    void findUserByIdIfUserNull() throws Exception {
        when(userGateway.findUserById("222"))
                .thenReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/222"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("user", Matchers.equalTo(userHasNullOfFields())))
                .andExpect(model().attribute("error_404", Matchers.equalTo("User not found")))
                .andExpect(view().name("user-form"))
                .andReturn();
        User actualUser = (User) Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("user");
        assertEquals(userHasNullOfFields(), actualUser);
    }

    @Test
    void saveUser() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1")
                        .param("userId","1")
                        .param("name","FreddyMercury")
                        .param("phoneNumber","+37525987")
                        .param("save", "push"))
                .andExpect(view().name("redirect:/users/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        verify(userGateway).saveUser(verifyUser());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/1")
                        .param("userId","1")
                        .param("name","FreddyMercury")
                        .param("phoneNumber","+37525987")
                        .param("delete", "push"))
                .andExpect(view().name("redirect:/users/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        verify(userGateway).deleteUserById("1");
    }

    private User createUser() {
        return new User("666", "AliceCooper", "+375291234");
    }

    private User verifyUser() {
        return new User("1", "FreddyMercury", "+37525987");
    }

    private User userHasNullOfFields() {
        return new User(null, null, null);
    }
}
