package com.learndirect.resultsportal.event;

import java.util.List;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class FeedTraverser {

    private final Predicate<Entry> stopPredicate;
    private final EntryVisitor visitor;
    private final Predicate<Entry> entriesMatching;
    private final FeedReader reader;
    private final Ordering<Entry> entryOrdering;

    FeedTraverser(Predicate<Entry> startPredicate, EntryVisitor visitor, FeedReader reader,
                  Predicate<Entry> entrysMatching, Ordering<Entry> latestToEarliest) {
        this.stopPredicate = startPredicate;
        this.visitor = visitor;
        this.reader = reader;
        this.entriesMatching = entrysMatching;
        this.entryOrdering = latestToEarliest;
    }

    /**
     * Traverse a feed found at location. Location maybe a url, file system location, string etc.... The actual meaning
     * of this is determined by the underlying {@link FeedReader}.
     * 
     * @param location
     */
    public void traverse(String location) {
        backwardsTraverse(reader.load(location));
    }

    // Traverse back through the archive from latest to earliest
    private void backwardsTraverse(Feed feed) {
        boolean stopped = false;
        List<Entry> seenEntries = Lists.newArrayList();

        for (Entry entry : entryOrdering.sortedCopy(feed.getEntries())) {
            if (stopPredicate.apply(entry)) {
                stopped = true;
                break;
            }
            seenEntries.add(entry);
        }

        if (stopped) {
            for (Entry seenEntry : Lists.reverse(seenEntries)) {
                if (entriesMatching.apply(seenEntry)) {
                    visitor.visit(seenEntry);
                }
            }
            Optional<Feed> next = reader.getNextArchive(feed);
            if (next.isPresent()) {
                forwardsTraverse(next.get());
            }
            return;
        }

        Optional<Feed> previous = reader.getPreviousArchive(feed);
        if (previous.isPresent()) {
            backwardsTraverse(previous.get());
        } else {
            forwardsTraverse(feed);
        }
    }

    // Traverse forwards through the feed from earliest to latest.
    private void forwardsTraverse(Feed feed) {
        for (Entry entry : Lists.reverse(entryOrdering.sortedCopy(feed.getEntries()))) {
            if (entriesMatching.apply(entry)) {
                visitor.visit(entry);
            }
        }
        Optional<Feed> next = reader.getNextArchive(feed);
        if (next.isPresent()) {
            forwardsTraverse(next.get());
        }
    }
}