package ru.itmo.wp.model.domain;

import java.io.Serializable;

public class Article extends AbstractDomain implements Serializable  {
    private long userId;
    private String title;
    private String text;
    private boolean hidden;

    public Article() {}

    public Article(long userId, String title, String text, boolean hidden) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.hidden = hidden;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
