package com.twitter.TwitterEduApp.search;

import com.twitter.TwitterEduApp.model.LightTweet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

// Stub jest to najprostsza implementacja zadanego interfejsu

@Configuration
public class StubTwitterSearchConfig {

    //@Primary
    @Bean
    public TwitterSearch twitterSearch() {
        return (searchType, keywords) -> Arrays.asList(
                new LightTweet("Treść tweeta"),
                new LightTweet("Treść innego tweeta")
        );
    }
}
