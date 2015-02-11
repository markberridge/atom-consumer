package com.learndirect.resultsportal.event;

import org.apache.abdera.model.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An interface that is used to process entries.
 */
public interface EntryVisitor {

    void visit(Entry entry);

    /**
     * An EntryVisitor that does nothing apart from log each visited entry
     */
    public static EntryVisitor noopVisitor = new EntryVisitor() {
        Logger log = LoggerFactory.getLogger(EntryVisitor.class);

        @Override
        public void visit(Entry entry) {
            log.debug("Visiting entry : {}, doing nothing.", entry);
        }
    };

}