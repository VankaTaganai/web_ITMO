package ru.itmo.wp.model.domain;

public class Event extends AbstractDomain {
    private long userId;
    private EventType type;

    public Event() {}

    public Event(long userId, EventType type) {
        this.userId = userId;
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
