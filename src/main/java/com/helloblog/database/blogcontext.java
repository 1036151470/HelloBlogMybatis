package com.helloblog.database;

public class blogcontext {

    private long id;
    private String title;
    private String createdtime;
    private String changetime;
    private String context;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getChangetime() {
        return changetime;
    }

    public void setChangetime(String changetime) {
        this.changetime = changetime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "blogcontext{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdtime='" + createdtime + '\'' +
                ", changetime='" + changetime + '\'' +
                ", context='" + context + '\'' +
                '}';
    }

}
