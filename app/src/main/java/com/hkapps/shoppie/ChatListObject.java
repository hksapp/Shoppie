package com.hkapps.shoppie;

/**
 * Created by vamshi on 16-03-2017.
 */

public class ChatListObject {
    private String username;
    ChatListObject(){

    }
    ChatListObject(String username){
        this.username =username;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
