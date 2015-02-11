package com.learndirect.resultsportal.event;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.SessionFactory;

import com.learndirect.resultsportal.event.EventFeedState;
import com.learndirect.resultsportal.event.EventFeedState.FeedType;

public class EventFeedStateDao extends AbstractDAO<EventFeedState> {

    public EventFeedStateDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public EventFeedState findByFeedType(FeedType feedType) {
        return get(feedType);
    }

    public void update(EventFeedState eventFeedState) {
        persist(eventFeedState);
    }
}