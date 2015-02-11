package com.learndirect.resultsportal.event;


import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.abdera.model.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;

/**
 * A feed reader that uses jersey to request feeds over http. 
 */
public class JerseyAtomFeedReader implements FeedReader {

    private static final Logger log = LoggerFactory.getLogger(JerseyAtomFeedReader.class);

    private final Client client;

    public JerseyAtomFeedReader(Client client) {
        checkNotNull(client, "Client can not be null!");
        this.client = client;
    }

    /** 
     * Note only supports Http urls.
     */
    @Override
    public Feed load(String link) {
        log.info("loading feed from {}", link);
        return client.resource(link).get(Feed.class);
    }

    @Override
    public Optional<Feed> getNextArchive(Feed feed) {
        if (feed.getLink("next-archive") == null) {
            return Optional.absent();
        }
        String nextLink = feed.getLink("next-archive").getHref().toString();
        return Optional.of(load(nextLink));
    }

    @Override
    public Optional<Feed> getPreviousArchive(Feed feed) {
        if (feed.getLink("prev-archive") == null) {
            return Optional.absent();
        }
        String nextLink = feed.getLink("prev-archive").getHref().toString();
        return Optional.of(load(nextLink));
    }

}