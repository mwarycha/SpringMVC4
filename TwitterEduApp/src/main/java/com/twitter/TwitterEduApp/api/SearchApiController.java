package com.twitter.TwitterEduApp.api;

import com.twitter.TwitterEduApp.model.LightTweet;
import com.twitter.TwitterEduApp.search.TwitterSearch;
import com.twitter.TwitterEduApp.service.SearchService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/search")
public class SearchApiController {

    private TwitterSearch searchService;

    @Autowired
    public SearchApiController(TwitterSearch twitterSearch) {
        this.searchService = twitterSearch;
    }

    @RequestMapping(value = "/{searchType}", method = RequestMethod.GET)
    public List<LightTweet> search(@PathVariable String searchType, @MatrixVariable List<String> keywords) {
        return searchService.search(searchType, keywords);
    }
}
