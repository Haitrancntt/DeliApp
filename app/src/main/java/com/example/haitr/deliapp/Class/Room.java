package com.example.haitr.deliapp.Class;

/**
 * Created by haitr on 12/26/2016.
 */
public class Room {
    String sName;
    String sID;

    public Room(String sName, String sID) {
        this.sName = sName;
        this.sID = sID;
    }

    public Room() {
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }
}
