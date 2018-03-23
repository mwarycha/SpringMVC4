package com.twitter.TwitterEduApp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.filter.ShallowEtagHeaderFilter;
import javax.servlet.Filter;

/**
 * Created by emawary on 2018-03-22.
 */

@Configuration
public class ETag {

    @Bean
    public Filter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
