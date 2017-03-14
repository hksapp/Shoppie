package com.hkapps.shoppie;

import android.graphics.Bitmap;

/**
 * Created by vamshi on 14-03-2017.
 */

public class CircleObject {
    private String username, gmail,userimage;
    public CircleObject(){


    }
    public CircleObject(String username, String gmail, String userimage) {
        this.username = username;
        this.gmail = gmail;
        this.userimage = userimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
