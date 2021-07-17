package ru.itmo.wp.model.domain;

public class Talk extends AbstractDomain {
    private long sourceUserId;
    private long targetUserId;
    private String text;

    public Talk() {}

    public Talk(long sourceUserId, long targetUserId, String text) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
        this.text = text;
    }

    public long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
