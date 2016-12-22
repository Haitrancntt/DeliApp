package com.example.haitr.deliapp.Class;

/**
 * Created by haitr on 12/14/2016.
 */
public class Contact {
    public String sName, sEmail;

    public Contact() {
        super();
    }

    public Contact(String sName, String sEmail) {
        super();
        this.sName = sName;
        this.sEmail = sEmail;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }
}
