/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Extract {

    public static Timespan getTimespan(List<Tweet> tweets) {
        if(tweets.isEmpty()) {
        	return new Timespan(null, null);
        }
        
        Instant my_startTime = tweets.get(0).getTimestamp(); 
        Instant my_endTime = tweets.get(0).getTimestamp(); 
 
        for (Tweet tweet : tweets) { 
            Instant timestamp = tweet.getTimestamp(); 
            my_startTime = timestamp.isBefore(my_startTime) ? timestamp : my_startTime;
            my_endTime = timestamp.isAfter(my_endTime) ? timestamp : my_endTime;
        } 
 
        return new Timespan(my_startTime, my_endTime); 
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    	 Set<String> expected_mentioned_users = new HashSet<>(); 
    	 
         for (Tweet single_tweet : tweets) { 
             String text = single_tweet.getText(); 
             String[] words = text.split("\\s+"); 
  
             for (String single_word : words) { 
                 if (single_word.startsWith("@") && single_word.length() > 1) { 
                     String mentionedUser = single_word.substring(1).replaceAll("[^a-zA-Z0-9_]", "").toLowerCase(); 
                     expected_mentioned_users.add(mentionedUser); 
                 } 
             }
         }
		return expected_mentioned_users;
    }

}
