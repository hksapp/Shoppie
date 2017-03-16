package com.hkapps.shoppie;

/**
 * Created by vamshi on 16-03-2017.
 */

public class ChatListObject {
    private String createdBy;
    ChatListObject(){

    }
    ChatListObject(String createdBy){
        this.createdBy=createdBy;
    }
    public String getCreatedBy() {
        return createdBy;
    }

    public void setUserId(String createdBy) {
        this.createdBy = createdBy;
    }
}
