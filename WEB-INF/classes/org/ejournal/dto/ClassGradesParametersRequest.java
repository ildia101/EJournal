package org.ejournal.dto;

public class ClassGradesParametersRequest {
    private int organization;
    private String classroom;
    private String subject;

    public ClassGradesParametersRequest(int organization, String classroom, String subject) {
        this.organization = organization;
        this.classroom = classroom;
        this.subject = subject;
    }

    public int getOrganization() {
        return organization;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getSubject() {
        return subject;
    }
}
