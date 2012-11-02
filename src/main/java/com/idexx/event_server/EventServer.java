package com.idexx.event_server;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.idexx.twitter.Tweet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: davek
 * Date: 10/28/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventServer extends javax.servlet.http.HttpServlet {
    final String filePath = "/home/davek/src/event-server/data/mnf";

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        List<Tweet> allTweets = readFiles(filePath);
        TweetQueue queue = new TweetQueue(allTweets);

        queue.start(new TweetTaskFactory(response));

        while(true) {

        }

    }

    private List<Tweet> readFiles(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> lines = Lists.newLinkedList();
        List<Tweet> allTweets = Lists.newLinkedList();

        try {
            File tweetDir = new File(filePath);
            String[] tweetFiles = tweetDir.list();
            Arrays.sort(tweetFiles);
            for (String tweetFile : tweetFiles) {
                lines.addAll(Files.readLines(new File(tweetDir.getCanonicalPath() + File.separator + tweetFile), Charset.defaultCharset()));
            }

            for (String line : lines) {
                allTweets.add(mapper.readValue(line, Tweet.class));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return allTweets;

    }
}
