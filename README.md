event-server
=============
This project was created to support an introductory [Storm](http://storm-project.net) talk that I
gave. Credit for the idea and some of the code comes from a
[screencast](http://storm.twitsprout.com) that was done by Adrian Petrescu. The screencast is
excellent.

The server will read tweets from files that were created by the
[client](http://github.com/dkincaid/twitter-client). It will read all the files in the `data/mnf`
directory and serve them up.

Usage
----
The project uses an internal Jetty web server that I use Maven to build and launch. So just run

    mvn jetty:run
    
from the root of the project and it will build and start the server on port 8080. Then just point
the browser (or better yet the Storm topology from
[mnf-storm](http://github.com/dkincaid/mnf-storm)) at `http://localhost:8080/replay` and watch it
go.

Customization
----
This was created for a very specific job, so there isn't any customization you can really do. There
is one interesting setting in the
[`TweetQueue`](https://github.com/dkincaid/event-server/blob/master/src/main/java/com/idexx/event_server/TweetQueue.java)
file. There is a `SPEED_FACTOR` constant there that will set how fast the server sends the
tweets. For example, if you set this to 60 then each second the server will send out a minute's
worth of tweets. When set to 1 the tweet timing will be the same as the tweets came in (1 second=1 second).
