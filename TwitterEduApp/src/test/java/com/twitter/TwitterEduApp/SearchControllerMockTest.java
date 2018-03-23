package com.twitter.TwitterEduApp;

import com.twitter.TwitterEduApp.controllers.SearchController;
import com.twitter.TwitterEduApp.model.LightTweet;
import com.twitter.TwitterEduApp.search.StubTwitterSearchConfig;
import com.twitter.TwitterEduApp.service.SearchService;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Created by emawary on 2018-03-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        TwitterEduAppApplication.class,
        StubTwitterSearchConfig.class
})
@WebAppConfiguration
public class SearchControllerMockTest {

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchController searchController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(searchController)
                .setRemoveSemicolonContent(false).build();
    }

    @Test
    public void should_search() throws Exception {

        when(searchService.search(anyString(),anyListOf(String.class))).
                thenReturn(Arrays.asList(new LightTweet("treśc tweeta")));

        this.mockMvc.perform(get("/search/mixed;keywords=spring"))
                .andExpect(status().isOk())
                .andExpect(view().name("resultPage"))
                .andExpect(model().attribute("tweets",everyItem(hasProperty("text",is("treśc tweeta")))));

        verify(searchService,times(1)).search(anyString(), anyListOf(String.class));
    }
}
