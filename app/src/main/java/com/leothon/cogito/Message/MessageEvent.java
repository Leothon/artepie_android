package com.leothon.cogito.Message;

/**
 * created by leothon on 9/26/2018.
 */
public class MessageEvent {
    private String message;
    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
