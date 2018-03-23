package com.twitter.TwitterEduApp.search;

import org.springframework.social.twitter.api.SearchParameters;

/**
 * Created by emawary on 2018-03-21.
 */
public class SearchParamsBuilder {

    public static SearchParameters createSearchParam(String searchType, String taste) {

        SearchParameters.ResultType resultType = getResultType(searchType);
        SearchParameters searchParameters      = new SearchParameters(taste);

        searchParameters.resultType(resultType);

        //@param count number of tweets to return
        searchParameters.count(3);
        return searchParameters;
    }
    private static SearchParameters.ResultType getResultType(String searchType) {

        for (SearchParameters.ResultType knownType : SearchParameters.ResultType.values()) {

            if (knownType.name().equalsIgnoreCase(searchType)) {
                return knownType;
            }
    }

        return SearchParameters.ResultType.RECENT;
    }
}