package com.twitter.TwitterEduApp.search;

import com.twitter.TwitterEduApp.model.LightTweet;

import java.util.List;

public interface TwitterSearch {
    List<LightTweet> search(String searchType, List<String> keywords);
}
