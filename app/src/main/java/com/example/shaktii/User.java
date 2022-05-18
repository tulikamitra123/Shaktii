package com.example.shaktii;

public class User {

    String email, contact1, contact2, contact3,contact4, contact5;

    public User(){}

    public User(String email, String contact1, String contact2, String contact3, String contact4, String contact5) {
        this.email = email;
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.contact3 = contact3;
        this.contact4 = contact4;
        this.contact5 = contact5;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact3() {
        return contact3;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }

    public String getContact4() {
        return contact4;
    }

    public void setContact4(String contact4) {
        this.contact4 = contact4;
    }

    public String getContact5() {
        return contact5;
    }

    public void setContact5(String contact5) { this.contact5 = contact5; }
}
