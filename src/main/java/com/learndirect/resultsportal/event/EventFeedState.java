package com.learndirect.resultsportal.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EVENT_FEED_STATE")
public class EventFeedState {

    public enum FeedType {
        EVENTS
    }

    @Id
    @Column(name = "FEED_NAME")
    @Enumerated(EnumType.STRING)
    private FeedType feedType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LAST_CONSUMED_FEED_ENTRY_ID")
    private String lastConsumedFeedId;

    @Column(name = "LAST_POLLED_DATE", nullable = false)
    private Long lastPolledTime;

    public EventFeedState(FeedType feedType, String lastConsumedFeedId) {
        this.feedType = feedType;
        this.lastConsumedFeedId = lastConsumedFeedId;
    }

    public EventFeedState() {
        // MarshallingConstructor
    }

    public FeedType getFeedType() {
        return feedType;
    }

    public String getLastConsumedFeedId() {
        return lastConsumedFeedId;
    }

    public void setLastConsumedFeedId(String lastConsumedFeedId) {
        this.lastConsumedFeedId = lastConsumedFeedId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((feedType == null) ? 0 : feedType.hashCode());
        result = prime * result + ((lastConsumedFeedId == null) ? 0 : lastConsumedFeedId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EventFeedState other = (EventFeedState) obj;
        if (feedType != other.feedType)
            return false;
        if (lastConsumedFeedId == null) {
            if (other.lastConsumedFeedId != null)
                return false;
        } else if (!lastConsumedFeedId.equals(other.lastConsumedFeedId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EventFeedState [feedType=" + feedType + ", lastConsumedFeedId=" + lastConsumedFeedId + "]";
    }

    public static class EventFeedStateBuilder {
        private FeedType feedType;
        private String lastConsumedFeedId;

        public EventFeedStateBuilder feedType(FeedType feedTypeIn) {
            this.feedType = feedTypeIn;
            return this;
        }

        public EventFeedStateBuilder lastConsumedFeedId(String lastConsumedFeedIdIn) {
            this.lastConsumedFeedId = lastConsumedFeedIdIn;
            return this;
        }

        public EventFeedState build() {
            return new EventFeedState(this);
        }
    }

    private EventFeedState(EventFeedStateBuilder builder) {
        this.feedType = builder.feedType;
        this.lastConsumedFeedId = builder.lastConsumedFeedId;
    }

    public Long getLastPolledTime() {
        return lastPolledTime;
    }

    public void setLastPolledTime(Long lastPolledTime) {
        this.lastPolledTime = lastPolledTime;
    }
}
