package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vamshi on 17-03-2017.
 */

public class MessagingHolder extends RecyclerView.ViewHolder {
    private static final String TAG = MessagingHolder.class.getSimpleName();
    public TextView message,sentby_username;
    public ImageView image;
    public LinearLayout message_layout;
    public MessagingHolder(View itemView) {
        super(itemView);
        message=(TextView)itemView.findViewById(R.id.message_id);
        image=(ImageView)itemView.findViewById(R.id.message_image);
        message_layout=(LinearLayout)itemView.findViewById(R.id.message_layoutId);
        sentby_username=(TextView)itemView.findViewById(R.id.sentby_user);
    }
}
