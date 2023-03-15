package com.example.vendorapp.Models;

public class jobModel {
    String title,department,payment;
    int experience;
    String jobtype,dutystation,specialskill,deadline,dateposted, timestamp, uid;

    public jobModel() {
    }

    public jobModel(String title, String department, String payment, int experience, String jobtype, String dutystation, String specialskill, String deadline, String dateposted, String timestamp, String uid) {
        this.title = title;
        this.department = department;
        this.payment = payment;
        this.experience = experience;
        this.jobtype = jobtype;
        this.dutystation = dutystation;
        this.specialskill = specialskill;
        this.deadline = deadline;
        this.dateposted = dateposted;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getDutystation() {
        return dutystation;
    }

    public void setDutystation(String dutystation) {
        this.dutystation = dutystation;
    }

    public String getSpecialskill() {
        return specialskill;
    }

    public void setSpecialskill(String specialskill) {
        this.specialskill = specialskill;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDateposted() {
        return dateposted;
    }

    public void setDateposted(String dateposted) {
        this.dateposted = dateposted;
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
