package com.example.vendorapp.Models;

public class Users {
    String accounttype;

    public Users() {
    }

    public Users(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }
}
