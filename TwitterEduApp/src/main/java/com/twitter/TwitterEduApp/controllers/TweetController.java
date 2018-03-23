package com.twitter.TwitterEduApp.controllers;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TweetController {

    @Autowired
    private Twitter twitter;

    @RequestMapping("/search")
    public String home() {
        return "searchPage";
    }

    @RequestMapping("/result")
    public String hello(@ModelAttribute("search") String search, Model model) {
        SearchResults searchResults = twitter.searchOperations().search(search);
        List<Tweet> tweets          = searchResults.getTweets();
        model.addAttribute("tweets", tweets);
        model.addAttribute("search", search);
        return "resultPage";
    }

    @RequestMapping(value="/postSearch", method= RequestMethod.POST)
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String search =request.getParameter("search");
        if(search.toLowerCase().contains("smieci")) {

            //After the redirect, flash attributes are automatically added to the model of the controller that serves the target URL.
            redirectAttributes.addFlashAttribute("error", "wystapil blad");
            return "redirect:/";
        }
        redirectAttributes.addAttribute("search", search);
        return "redirect:result";
    }

}