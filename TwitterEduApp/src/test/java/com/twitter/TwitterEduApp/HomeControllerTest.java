package com.twitter.TwitterEduApp;

/**
 * Created by emawary on 2018-02-20.
 */

import com.twitter.TwitterEduApp.profile.UserProfileSession;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext wac;

    // do symulowania interakcji z kontrolerem bez obciążenia wprowadzanego przez kontener
    // serwletów zastosowana jest klasa MockMvc
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_redirect_to_profile() throws Exception {
        this.mockMvc.perform(get("/"))

             //informacje diagnostyczne
            .andDo(print())

            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/profile"));
    }

    @Test
    public void should_rediredt_to_tastes() throws Exception {

        MockHttpSession mockHttpSesion        = new MockHttpSession();
        UserProfileSession userProfileSession = new UserProfileSession();
        userProfileSession.setTastes(Arrays.asList("spring", "groovy"));

        //Spring saves the session scope bean as a session attribute "scopedTarget.${idOfTheBean}"
        mockHttpSesion.setAttribute("scopedTarget.userProfileSession", userProfileSession);

        this.mockMvc.perform(get("/").session(mockHttpSesion))

                //informacje diagnostyczne
                .andDo(print())

                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/search/mixed;keywords=spring,groovy"));
    }

    @Test
    public void should_rediredt_to_tastes_session_builder() throws Exception {

        MockHttpSession session = new SessionBuilder().userTastes("spring,groovy").build();

        this.mockMvc.perform(get("/")
                .session(session)).andExpect(status().isFound())
                .andExpect(redirectedUrl(("/search/mixed;keywords=spring,groovy")));
    }
}
