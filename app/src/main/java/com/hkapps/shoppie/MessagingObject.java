package com.hkapps.shoppie;

/**
 * Created by vamshi on 17-03-2017.
 */

public class MessagingObject {
    private String message,sentBy, messageType;
    MessagingObject(){

    }
    MessagingObject(String message,String sentBy,String messageType){
        this.message=message;
        this.sentBy=sentBy;
        this.messageType = messageType;
    }
    public String getMessage(){        return message;    }
    public void setMessage(String message){this.message=message;}
    public String getSentBy(){        return sentBy;    }
    public void setSentBy(String sentBy){this.sentBy=sentBy;}

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
