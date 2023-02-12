package com.tutrit.webclient.controller;

import com.tutrit.webclient.bean.User;
import com.tutrit.webclient.gateway.EngineerGateway;
import org.hamcrest.Matchers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
class HomePageControllerTest {

    @MockBean
    EngineerGateway engineerGateway;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;

//    /**
//     * @see <a href="https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/test-mockmvc.html">Setting Up MockMvc and Spring Security</a>
//     */
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    /*
    It is good practice having 'name' or 'id' attributes on key elements.
     */
    @Test
//    @WithMockUser
    void openHomePage() throws Exception {
        when(engineerGateway.getEngineer()).thenReturn(new User("Mikas", 27));
        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
//                .andDo(MockMvcResultHandlers.print()) // it could be used for debug, but it prints whole page
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("user", Matchers.equalTo(makeUser())))
                .andExpect(view().name("index"))
                .andReturn();

        User actualUser = (User) result.getModelAndView().getModel().get("user");
        Assertions.assertEquals(makeUser(), actualUser);

        String html = result.getResponse().getContentAsString();
        Assertions.assertTrue(html.contains("<h1 id='user-data' class='m-0'>Mikas 27</h1>"));
        Document doc = Jsoup.parse(html);
        final Elements userNameById = doc.selectXpath("//*[@id='user-data']");
        final Elements userNameByFullPath = doc.selectXpath("/html/body/div/div[1]/div[1]/div/div/div[1]/h1");
        Assertions.assertEquals("<h1 id=\"user-data\" class=\"m-0\">Mikas 27</h1>", userNameByFullPath.get(0).outerHtml());
        Assertions.assertEquals("Mikas 27", userNameByFullPath.get(0).text());
    }

//    @Test
    void saveEngineer() {
    }

//    @Test
    void testOpenHomePage() {
    }

    private User makeUser() {
        return new User("Mikas", 27);
    }
}