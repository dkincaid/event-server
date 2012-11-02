package com.idexx.event_server;

import com.idexx.twitter.Tweet;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: davek
 * Date: 10/28/12
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TweetQueue {
    private static final int SPEED_FACTOR = 1;

    private final PriorityQueue<Tweet> queue;
    private final DateTime startTime;
    private Timer tweetTimer;

    public TweetQueue(List<Tweet> allTweets) {
        this.queue = new PriorityQueue<Tweet>(
                Math.max(1, allTweets.size()),
                new Comparator<Tweet>() {
                    @Override
                    public int compare(Tweet o1, Tweet o2) {
                        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
                    }
                });

        queue.addAll(allTweets);
        System.out.println("Tweets queued: " + allTweets.size());
        this.startTime = queue.isEmpty() ? null : queue.peek().getCreatedAt();
        System.out.println("Start time: " + startTime);
    }

    public void start(TweetTaskFactory tweetTaskFactory) {
        tweetTimer = new Timer("Tweet timer", false);


        while (!queue.isEmpty()) {
            Tweet tweet = queue.poll();
            tweetTimer.schedule(
                    tweetTaskFactory.create(tweet),
                    new Duration(startTime, tweet.getCreatedAt()).getMillis() / SPEED_FACTOR);
        }
    }

}
