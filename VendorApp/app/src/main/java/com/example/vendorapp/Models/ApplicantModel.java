package com.example.vendorapp.Models;

public class ApplicantModel {
    String applicationID, applicationTime, Status, appliedBy, appliedTo, name, email, phone, timestamp;

    public ApplicantModel() {
    }

    public ApplicantModel(String applicationID, String applicationTime, String status, String appliedBy, String appliedTo, String name, String email, String phone, String timestamp) {
        this.applicationID = applicationID;
        this.applicationTime = applicationTime;
        this.Status = status;
        this.appliedBy = appliedBy;
        this.appliedTo = appliedTo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.timestamp = timestamp;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAppliedBy() {
        return appliedBy;
    }

    public void setAppliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
    }

    public String getAppliedTo() {
        return appliedTo;
    }

    public void setAppliedTo(String appliedTo) {
        this.appliedTo = appliedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
