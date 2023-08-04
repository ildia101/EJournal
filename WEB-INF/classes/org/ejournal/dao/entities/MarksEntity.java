package org.ejournal.dao.entities;

public class MarksEntity {
    private String organization;
    private String classroom;
    private String subject;
    private int page;
    private String dates[];
    private String marks[][];

    public MarksEntity(String organization, String classroom, String subject, int page, String[] dates, String[][] marks) {
        this.organization = organization;
        this.classroom = classroom;
        this.subject = subject;
        this.page = page;
        this.dates = dates;
        this.marks = marks;
    }

    public String getOrganization() {
        return organization;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getSubject() {
        return subject;
    }

    public int getPage() {
        return page;
    }

    public String[] getDates() {
        return dates;
    }

    public String[][] getMarks() {
        return marks;
    }
}
