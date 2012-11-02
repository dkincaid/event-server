package com.idexx.event_server;

import com.idexx.twitter.Tweet;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: davek
 * Date: 10/28/12
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TweetTaskFactory {

    private HttpServletResponse response;
    private Writer writer;

    public TweetTaskFactory(HttpServletResponse response) {
        this.response = response;
        try {
            this.writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TimerTask create(final Tweet tweet) {
        final ObjectMapper mapper = new ObjectMapper();

         return new TimerTask() {

             @Override
             public void run() {
                 mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

                 try {
                     StringWriter sw = new StringWriter();
                     mapper.writeValue(sw, tweet);
                     writer.write(sw.toString());
                     writer.write("\n");
                     response.flushBuffer();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         };
    }
}
