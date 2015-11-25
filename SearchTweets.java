/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j.examples.search;

import twitter4j.*;

import java.util.List;
import java.util.Formatter;
import java.util.Date;
import java.lang.String;
/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class SearchTweets {
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args search query
     */
    public static void main(String[] args) {
        /*if (args.length < 1) {
            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
            System.exit(-1);
        }*/
        Twitter twitter = new TwitterFactory().getInstance();
        try {
        	Query query = new Query(" ").since("2015-11-25");
            QueryResult statuses;
            do {
            	//QueryResult result = searchWithRetry(twitter, query); //tweet.getUser().getScreenName()  searchWithRetry is my function that deals with rate limits
            	//result = twitter.search(query);
            	statuses = twitter.search(query);
                List<Status> tweets = statuses.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("DATE: "+tweet.getCreatedAt() + " - " + tweet.getText());
                }
            } while ((query = statuses.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }
}
