package com.hkapps.shoppie;

/**
 * Created by vamshi on 16-03-2017.
 */

public class ChatListObject {
    private String username,userImageUrl;
    ChatListObject(){

    }
    ChatListObject(String username, String userImageUrl){
        this.username =username;
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

  /*  ChatListObject(ImageView userImageUrl) {this.userImageUrl= userImageUrl;}
    public ImageView getUserImageUrl(){return userImageUrl;}
    public  void setUserImageUrl(ImageView userImageUrl){this.userImageUrl = userImageUrl;}*/
}
