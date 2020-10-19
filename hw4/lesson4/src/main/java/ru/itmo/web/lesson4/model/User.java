package ru.itmo.web.lesson4.model;

public class User {
    public enum Color {RED, GREEN, BLUE}

    private final long id;
    private final String handle;
    private final String name;
    private final Color color;
    private long postsCount;


    public User(long id, String handle, String name, Color color) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.color = color;
        postsCount = 0;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public long getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(long postsCount) {
        this.postsCount = postsCount;
    }
}
