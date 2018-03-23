package com.twitter.TwitterEduApp.search;

/**
 * Created by emawary on 2018-03-22.
 */
import com.twitter.TwitterEduApp.comparators.SortAlphabet;
import com.twitter.TwitterEduApp.model.LightTweet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Primary;

@Service
@Profile("async")
@Primary
public class ParallelSearchService implements TwitterSearch {

    private final AsyncSearch asyncSearch;

    @Autowired
    public ParallelSearchService(AsyncSearch asyncSearch) {
        this.asyncSearch = asyncSearch;
    }

    @Override
    public List<LightTweet> search(String searchType, List<String> keywords) {

        CountDownLatch latch       = new CountDownLatch(keywords.size());
        List<LightTweet> allTweets = Collections.synchronizedList(new ArrayList<>());
        keywords.stream().forEach(keyword -> asyncFetch(latch, allTweets, searchType, keyword));
        await(latch);

        allTweets.sort(new SortAlphabet());

        return allTweets;
    }

    private void asyncFetch(CountDownLatch latch, List<LightTweet> allTweets, String searchType, String keyword) {

                asyncSearch.asyncFetch(searchType, keyword)
                .addCallback(
                        tweets -> onSuccess(allTweets, latch, tweets),
                        ex     -> onError(latch, ex));
    }

    private void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void onSuccess(List<LightTweet> results, CountDownLatch latch, List<LightTweet> tweets) {
        final Log logger = LogFactory.getLog(ParallelSearchService.class);

        results.addAll(tweets);
        latch.countDown();
        logger.info(latch);
    }

    private static void onError(CountDownLatch latch, Throwable ex) {
        ex.printStackTrace();
        latch.countDown();
    }

}