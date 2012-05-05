/*
 * Copyright 2012 The Play! Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package actors;

import akka.actor.UntypedActor;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import models.HistoricalEvent;
import play.Logger;
import play.Play;
import utils.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class FeedCreationActor extends UntypedActor
{
    public final Map<String, String> feedDetails = new LinkedHashMap<String, String>();

    public FeedCreationActor()
    {
        feedDetails.put("atom_1.0",
                        "atom");
        feedDetails.put("rss_1.0",
                        "rss1");
        feedDetails.put("rss_2.0",
                        "rss2");
    }

    @Override
    public void onReceive(Object o) throws Exception
    {
        List<HistoricalEvent> historicalEvents = HistoricalEvent.findMostRecent(10);
        File feedDir = Play.application().getFile("public/feeds");
        boolean directoryExists = feedDir.exists();
        if (!directoryExists)
        {
            directoryExists = feedDir.mkdir();
            Logger.debug(String.format("Create feed directory [%s]",
                                       directoryExists ? "OK" : "Failed"));
        }

        if (directoryExists)
        {
            for (Map.Entry<String, String> entry: feedDetails.entrySet())
            {
                createFeed(historicalEvents,
                           entry.getKey(),
                           feedDir,
                           entry.getValue());
            }
        }
    }

    private void createFeed(List<HistoricalEvent> historicalEvents,
                            String feedType,
                            File outputDirectory,
                            String classifier)
    {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType(feedType);
        feed.setTitle("Play! Modules");
        feed.setLink("http://modules.playframework.org");
        feed.setUri("http://modules.playframework.org");
        feed.setPublishedDate(new Date());
        feed.setDescription("The Play! Framework's module repository feed");

        List<SyndEntry> entries = new ArrayList<SyndEntry>(historicalEvents.size());
        for (HistoricalEvent historicalEvent : historicalEvents)
        {
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(historicalEvent.category);
            entry.setAuthor("Play framework modules");
            entry.setPublishedDate(historicalEvent.creationDate);
            // todo this will be the url of the module
            entry.setLink("http://modules.playframework.org");
            entry.setUri("mpo-he-" + historicalEvent.id);
            SyndContent description = new SyndContentImpl();
            description.setType("text/plain");
            description.setValue(historicalEvent.message);
            entry.setDescription(description);
            entries.add(entry);
        }

        feed.setEntries(entries);

        Writer writer = null;
        try
        {
            File outputFile = new File(outputDirectory,
                                       String.format("mpo.%s.xml",
                                                     classifier));
            writer = new FileWriter(outputFile);
            SyndFeedOutput output = new SyndFeedOutput();
            output.output(feed,writer);
            writer.close();
        }
        catch (IOException e)
        {
            Logger.error(String.format("A problem occurred when generating the %s feed",
                                       classifier),
                         e);
        }
        catch (FeedException e)
        {
            Logger.error(String.format("A problem occurred when generating the %s feed",
                                       classifier),
                         e);
        }
        finally
        {
            IOUtils.close(writer);
        }
    }
}
