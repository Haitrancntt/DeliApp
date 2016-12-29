package com.example.haitr.deliapp.Class;

/**
 * Created by haitr on 12/26/2016.
 */
public class Message {
    private int id;
    private String messid;
    private String name;
    private String message;

    public Message(int id, String name, String message, String messid) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.messid = messid;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessid() {
        return messid;
    }

    public void setMessid(String messid) {
        this.messid = messid;
    }
}
