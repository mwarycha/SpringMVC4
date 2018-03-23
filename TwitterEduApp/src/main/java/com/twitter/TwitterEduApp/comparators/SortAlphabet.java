package com.twitter.TwitterEduApp.comparators;

import com.twitter.TwitterEduApp.model.LightTweet;

import java.util.Comparator;

/**
 * Created by emawary on 2018-03-22.
 */
public class SortAlphabet implements Comparator<LightTweet> {
    @Override
    public int compare(LightTweet o1, LightTweet o2) {
        return o1.getText().compareTo(o2.getText()) ;
    }
}
