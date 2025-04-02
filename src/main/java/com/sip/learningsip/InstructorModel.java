package com.sip.learningsip;

import javafx.beans.property.*;

public class InstructorModel {

    private final IntegerProperty id;
    private final StringProperty fullName;
    private final StringProperty email;
    private final IntegerProperty courseId;

    // Constructor
    public InstructorModel(int id, String fullName, String email, int courseId) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.courseId = new SimpleIntegerProperty(courseId);
    }

    // Getters and Property Methods
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public int getCourseId() {
        return courseId.get();
    }

    public IntegerProperty courseIdProperty() {
        return courseId;
    }
}