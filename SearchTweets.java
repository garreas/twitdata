import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.lang.String;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SearchTweets {
    
    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("1u2Ym7nQM4fJ0eouppxWEn3XA")
        .setOAuthConsumerSecret("4qPczZctmmzDWhv3cObg1CjyDOwy5Sg2A9bu5damtLYBrYtg27")
        .setOAuthAccessToken("2422678932-38eGOOGma5rWt1HuXjUPEjs2vEcAyPkLsUPYEJU")
        .setOAuthAccessTokenSecret("aojWESzlrNr9qvdRzSmUKGEljaNBvBzrq8cD1Hq432cJi");
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        try {
        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	Date date = new Date();
        	Query query = new Query(args[0]).since(dateFormat.format(date));
            QueryResult statuses;
            do {
            	statuses = twitter.search(query);
                List<Status> tweets = statuses.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("DATE: " + tweet.getCreatedAt() + " - " + tweet.getText());
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
