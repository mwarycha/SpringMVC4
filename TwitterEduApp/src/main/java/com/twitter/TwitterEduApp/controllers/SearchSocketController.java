package com.twitter.TwitterEduApp.controllers;

import com.twitter.TwitterEduApp.model.LightTweet;
import com.twitter.TwitterEduApp.search.AsyncSearch;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.social.twitter.api.SearchParameters;
import java.util.function.Consumer;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;

/**
 * Created by emawary on 2018-03-23.
 */
@Controller
public class SearchSocketController {

    @Autowired
    private AsyncSearch           asyncSearch;

    private SimpMessagingTemplate webSocket;

    @Autowired
    public SearchSocketController(AsyncSearch asyncSearch, SimpMessagingTemplate webSocket) {
        this.asyncSearch = asyncSearch;
        this.webSocket   = webSocket;
    }

    @MessageMapping("/search")
    public void search(@RequestParam List<String> keywords) throws Exception {
        Consumer<List<LightTweet>> callback = tweet -> webSocket.convertAndSend("/topic/searchResults", tweet);
        twitterSearch(SearchParameters.ResultType.POPULAR, keywords, callback);
    }

    public void twitterSearch(SearchParameters.ResultType resultType, List<String> keywords, Consumer<List<LightTweet>> callback) {
            keywords.stream().forEach(keyword -> {asyncSearch.asyncFetch(resultType.toString(), keyword).addCallback(callback::accept, Throwable::printStackTrace);
        });
    }
}