package com.hkapps.shoppie;

/**
 * Created by Pranav on 28-03-2017.
 */

public class NotificationObject {

    private String current_list_id,friend_name,friend_user_id,list_ref;
    private long timestamp;

   public NotificationObject(){

    }

    public NotificationObject(String current_list_id,String friend_name,String friend_user_id,long timestamp, String list_ref){

        this.current_list_id = current_list_id;
        this.friend_name = friend_name;
        this.friend_user_id = friend_user_id;
        this.timestamp = timestamp;
        this.list_ref = list_ref;

    }

    public String getCurrent_list_id() {return current_list_id;}

    public void setCurrent_list_id(String current_list_id) {this.current_list_id = current_list_id;}


    public String getFriend_name() {return friend_name;}

    public void setFriend_name(String friend_name) {this.friend_name = friend_name;}


    public String getFriend_user_id() {return friend_user_id;}

    public void setFriend_user_id(String friend_user_id) {this.friend_user_id = friend_user_id;}


    public long getTimestamp() {return timestamp;}

    public void setTimestamp(long timestamp) {this.timestamp = timestamp;}


    public String getList_ref() {return list_ref;}

    public void setList_ref(String list_ref) {this.list_ref = list_ref;}


}
