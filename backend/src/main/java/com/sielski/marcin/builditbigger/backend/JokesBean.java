package com.sielski.marcin.builditbigger.backend;

/** The object model for the data we are sending through endpoints */
@SuppressWarnings("unused")
public class JokesBean {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}