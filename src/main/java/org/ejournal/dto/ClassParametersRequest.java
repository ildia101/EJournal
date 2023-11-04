package org.ejournal.dto;

public class ClassParametersRequest {
    private String classNumber;
    private String classLetter;
    private int numberOfStudents;

    public ClassParametersRequest(String classNumber, String classLetter, int numberOfStudents) {
        this.classNumber = classNumber;
        this.classLetter = classLetter;
        this.numberOfStudents = numberOfStudents;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public String getClassLetter() {
        return classLetter;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }
}
