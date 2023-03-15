package com.example.vendorapp.Models;

public class ApprovedModel {
    String applicationID, applicationTime, Status;
    int experience, prevexperience;
    String age, appliedBy, appliedTo, approvedstatus, name, email, phone, timestamp;

    public ApprovedModel() {
    }

    public ApprovedModel(String applicationID, String applicationTime, String status, int experience, int prevexperience, String age, String appliedBy, String appliedTo, String approvedstatus, String name, String email, String phone, String timestamp) {
        this.applicationID = applicationID;
        this.applicationTime = applicationTime;
        Status = status;
        this.experience = experience;
        this.prevexperience = prevexperience;
        this.age = age;
        this.appliedBy = appliedBy;
        this.appliedTo = appliedTo;
        this.approvedstatus = approvedstatus;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getPrevexperience() {
        return prevexperience;
    }

    public void setPrevexperience(int prevexperience) {
        this.prevexperience = prevexperience;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getApprovedstatus() {
        return approvedstatus;
    }

    public void setApprovedstatus(String approvedstatus) {
        this.approvedstatus = approvedstatus;
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
