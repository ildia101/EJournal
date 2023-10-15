package org.ejournal.dto;

public class NewSchoolSubjectParametersRequest {
    private String subjectName;
    private String buttonText;

    public NewSchoolSubjectParametersRequest(String subjectName, String buttonText) {
        this.subjectName = subjectName;
        this.buttonText = buttonText;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getButtonText() {
        return buttonText;
    }
}
