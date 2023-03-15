package com.example.vendorapp.Models;

public class Amodel {
    String title, payment;
    int experience;
    String jobtype, educationlevel, specialskill, age, timestamp, uid;

    public Amodel() {
    }

    public Amodel(String title, String payment, int experience, String jobtype, String educationlevel, String specialskill, String age, String timestamp, String uid) {
        this.title = title;
        this.payment = payment;
        this.experience = experience;
        this.jobtype = jobtype;
        this.educationlevel = educationlevel;
        this.specialskill = specialskill;
        this.age = age;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getEducationlevel() {
        return educationlevel;
    }

    public void setEducationlevel(String educationlevel) {
        this.educationlevel = educationlevel;
    }

    public String getSpecialskill() {
        return specialskill;
    }

    public void setSpecialskill(String specialskill) {
        this.specialskill = specialskill;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}