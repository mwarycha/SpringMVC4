package com.twitter.TwitterEduApp.controllers;

import com.twitter.TwitterEduApp.search.TwitterSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import com.twitter.TwitterEduApp.service.SearchService;
import com.twitter.TwitterEduApp.model.LightTweet;

@Controller
public class SearchController {

    private TwitterSearch twitterSearch;

    @Autowired
    public SearchController(TwitterSearch searchService) {
        this.twitterSearch = searchService;
    }

    @RequestMapping("/search/{searchType}")
    public ModelAndView search(@PathVariable String searchType, @MatrixVariable List<String> keywords) {
        List<LightTweet> tweets = twitterSearch.search(searchType, keywords);
        ModelAndView modelAndView = new ModelAndView("resultPage");
        modelAndView.addObject("tweets", tweets);
        modelAndView.addObject("search", String.join(",", keywords));
        return modelAndView;
    }
}
