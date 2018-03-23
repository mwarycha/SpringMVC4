package com.twitter.TwitterEduApp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by EMAWARY on 2018-03-05.
 */

@Component
public class Helper {

    @Autowired
    private Twitter twitter;

    static private Twitter twitterAutowired;

    @PostConstruct
    private void init() {
        twitterAutowired = this.twitter;
    }

    public static Twitter getTwitterAutowired() {
        return twitterAutowired;
    }
}
