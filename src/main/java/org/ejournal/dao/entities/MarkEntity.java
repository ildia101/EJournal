package org.ejournal.dao.entities;

public class MarkEntity {
    private int id;
    private String date;
    private String mark;

    public MarkEntity(int id, String date, String mark) {
        this.id = id;
        this.date = date;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getMark() {
        return mark;
    }
}
