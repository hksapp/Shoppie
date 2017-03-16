package com.hkapps.shoppie;

/**
 * Created by vamshi on 14-03-2017.
 */

public class CircleObject {
    private String username, email, userImageUrl;
    public CircleObject(){


    }
    public CircleObject(String username, String email, String userImageUrl) {
        this.username = username;
        this.email = email;
        this.userImageUrl = userImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
