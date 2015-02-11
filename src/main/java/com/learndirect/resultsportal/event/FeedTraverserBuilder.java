package com.learndirect.resultsportal.event;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.abdera.model.Entry;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Ordering;

/**
 * A Feed Traverser performs a callback method on each visited entry in order of publishing.<br/>
 * <p>
 * It will traverse through each feed and follow prev-archive and next-archive links to visit the entire history of
 * events.
 * </p>
 * <p>
 * This walks backwards in time, through the feed until it finds an entry that matches its starting predicate:
 * {@link FeedTraverserBuilder#foundStartingEntryWhen(Predicate)}, or if it finishes traversing through all of the
 * feeds. It then goes forwards in time, processing entries that match
 * {@link FeedTraverserBuilder#processEntriesWhich(Predicate)}. will determine which entries should be processed.
 * </p>
 * <br/>
 * <p>
 * It will perform an action: {@link EntryVisitor} on each visited entry
 * </p>
 * <br/>
 * <p>
 * By default, all entries will be processed with a No-Operation EntryVisitor. No particular entry will start forward
 * traversal, it will travel backwards until it reaches the first ever entry and then process each entry in turn until
 * the last published entry.
 * </p>
 */
@SuppressWarnings("javadoc")
public class FeedTraverserBuilder {

    private final FeedReader reader;
    private Predicate<Entry> startingPredicate = Predicates.alwaysFalse();
    private Predicate<Entry> entriesMatching = Predicates.alwaysTrue();
    private Ordering<Entry> latestToEarliestOrdering = EntryFunctions.latestToEarliest();
    private EntryVisitor visitor = EntryVisitor.noopVisitor;

    private FeedTraverserBuilder(FeedReader reader) {
        checkNotNull(reader, "Reader can not be null!");
        this.reader = reader;
    }

    /**
     * Create a feed traverser, using a custom FeedReader to retrieve feeds
     */
    public static FeedTraverserBuilder createFeedTraverser(FeedReader reader) {
        return new FeedTraverserBuilder(reader);
    }

    /**
     * Register a predicate that triggers forward processing of the feed. This would generally match the last previously
     * read entry that has been processed. This defaults to {@link Predicates#alwaysFalse()} meaning the traverser will
     * visit all events.
     * 
     * @see {@link EntryFunctions#idEquals(String)}
     */
    public FeedTraverserBuilder foundLastProcessedEntryWhen(Predicate<Entry> startingOn) {
        this.startingPredicate = startingOn;
        return this;
    }

/**
     * Register a predicate that will determine which entries should be
     * processed. This can allow clients to determine the specific types of
     * event they wish to process. This defaults to {@link Predicates#alwaysTrue() meaning the
     * traverser will process all events.
     * 
     * @see {@link EntryFunctions#haveACategoryOf(String...)}
     */
    public FeedTraverserBuilder processEntriesWhich(Predicate<Entry> entries) {
        this.entriesMatching = entries;
        return this;
    }

    /***
     * Specify the order in which entries in the feed are processed - latest to earliest. This defaults to
     * {@link EntryFunctions#earliestToLatest()} ensuring that all entries are ordered by updated date
     */
    public FeedTraverserBuilder entriesOrderedBy(Ordering<Entry> ordering) {
        this.latestToEarliestOrdering = ordering;
        return this;
    }

    /**
     * Register an object to process entries. Defaults to {@Link EntryVisitor#noopVisitor} which only logs each
     * procesed entry.
     */
    public FeedTraverserBuilder whenFound(EntryVisitor entryVisitor) {
        this.visitor = entryVisitor;
        return this;
    }

    /**
     * Create the {@link FeedTraverser}
     */
    public FeedTraverser build() {
        return new FeedTraverser(startingPredicate, visitor, reader, entriesMatching, latestToEarliestOrdering);
    }
}