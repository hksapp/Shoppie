package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vamshi on 16-03-2017.
 */

public class ChatListHolder extends RecyclerView.ViewHolder {
    private static final String TAG =CircleHolder.class.getSimpleName();
    public TextView username;
    public LinearLayout chatid;
    public ImageView userImageUrl;
    public ChatListHolder(View itemView) {
        super(itemView);
        username = (TextView)itemView.findViewById(R.id.chat_userName);
        chatid=(LinearLayout)itemView.findViewById(R.id.chatid);
        userImageUrl =(ImageView) itemView.findViewById(R.id.userImageUrlchat);
    }
}
