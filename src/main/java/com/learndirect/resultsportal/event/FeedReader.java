package com.learndirect.resultsportal.event;

import org.apache.abdera.model.Feed;

import com.google.common.base.Optional;

/**
 * An object that loads and deserializes feeds.
 */
public interface FeedReader {

    /**
     * Load a feed from a specific location
     * 
     * @param location
     * @return
     */
    Feed load(String location);

    /**
     * Load the next archive in relation to the passed in feed
     * 
     * @param feed
     * @return
     */
    Optional<Feed> getNextArchive(Feed feed);

    /**
     * Load the previous archive in relation to the passed in feed
     * 
     * @param feed
     * @return
     */
    Optional<Feed> getPreviousArchive(Feed feed);

}