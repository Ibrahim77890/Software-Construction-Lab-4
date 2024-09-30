/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test 
    public void getSingleTweetTimeSpan() { 
        // THis test asserts when only single tweet is available in the input list of tweets
        Timespan timespan = Extract.getTimespan(Collections.singletonList(tweet1)); 
        assertEquals( d1, timespan.getStart()); 
        assertEquals( d1, timespan.getEnd()); 
    } 
 
    @Test 
    public void getSingleMentionedUser() { 
        // This test asserts when certain tweet metnions a user in it 
        Tweet tweet = new Tweet(3, "nigger", "Hey @nigger, how are you?", Instant.parse("2024-02-25T12:30:00Z")); 
        Set<String> mentionedUsers = Extract.getMentionedUsers(Collections.singletonList(tweet)); 
        Set<String> expectedMentions = new HashSet<>(Arrays.asList("nigger")); 
        assertEquals("mentioned user should be in the set", expectedMentions, mentionedUsers); 
    } 
 
    @Test 
    public void getMutlipleMentionedUsers() { 
        // This is the test when different tweets mention different users in them
        Tweet tweet1 = new Tweet(4, "nigger", "Hi @nigger!", Instant.parse("2024-02-25T12:30:00Z")); 
        Tweet tweet2 = new Tweet(5, "bigger", "Hello @bigger!", Instant.parse("2024-02-25T12:30:00Z")); 
        Set<String> my_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2)); 
        Set<String> my_expectedMentions = new HashSet<>(Arrays.asList("nigger", "bigger")); 
        assertEquals("mentioned users in the set", my_expectedMentions, my_mentionedUsers); 
    }
}
