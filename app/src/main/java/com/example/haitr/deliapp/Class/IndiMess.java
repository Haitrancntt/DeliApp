package com.example.haitr.deliapp.Class;

/**
 * Created by haitr on 12/29/2016.
 */
public class IndiMess {
    private String sNamesend, sNamere, sMessage;

    public IndiMess(String sNamesend, String sNamere, String sMessage) {
        this.sNamesend = sNamesend;
        this.sNamere = sNamere;
        this.sMessage = sMessage;
    }

    public IndiMess() {
    }

    public String getsNamesend() {
        return sNamesend;
    }

    public void setsNamesend(String sNamesend) {
        this.sNamesend = sNamesend;
    }

    public String getsNamere() {
        return sNamere;
    }

    public void setsNamere(String sNamere) {
        this.sNamere = sNamere;
    }

    public String getsMessage() {
        return sMessage;
    }

    public void setsMessage(String sMessage) {
        this.sMessage = sMessage;
    }
}
