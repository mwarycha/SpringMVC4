package com.twitter.TwitterEduApp;

import com.twitter.TwitterEduApp.search.StubTwitterSearchConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;

/*

    A good place to start is now probably: Testing improvements in Spring Boot 1.4.

    They describe a basic sample like the following:

    @RunWith(SpringRunner.class)
    @SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
    public class MyTest {
    }

    as a replacement to, one of many:

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(MyApp.class)
    @WebIntegrationTest
    public class MyTest {
    }

 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        TwitterEduAppApplication.class,
        StubTwitterSearchConfig.class
})
@WebAppConfiguration
public class SearchControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_search() throws Exception {

        this.mockMvc.perform(get("/search/mixed;keywords=spring"))
                .andExpect(status().isOk())
                .andExpect(view().name("resultPage"))
                .andExpect(model().attribute("tweets", hasSize(2)))
                .andExpect(model().attribute("tweets",
                                hasItems(
                                        hasProperty("text", is("Treść tweeta")),
                                        hasProperty("text", is("Treść innego tweeta"))
                                ))
                );
    }
}