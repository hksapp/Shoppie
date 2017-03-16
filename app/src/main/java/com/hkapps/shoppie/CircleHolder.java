package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by vamshi on 14-03-2017.
 */

public class CircleHolder extends RecyclerView.ViewHolder {
    private static final String TAG =CircleHolder.class.getSimpleName();
    public TextView userName,mailId;
    public ImageView userimage,removeUserFromList;
    public CircleHolder(View itemView) {
        super(itemView);
        userName=(TextView)itemView.findViewById(R.id.username_incirclelist);
        mailId=(TextView)itemView.findViewById(R.id.gmail_id_incircle_list);
        userimage=(ImageView)itemView.findViewById(R.id.userimage_incirclelist);
        removeUserFromList=(ImageView)itemView.findViewById(R.id.remove_from_circle);
    }

}
