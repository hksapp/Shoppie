package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vamshi on 17-03-2017.
 */

public class MessagingHolder extends RecyclerView.ViewHolder {
    private static final String TAG = MessagingHolder.class.getSimpleName();
    public TextView message;
    public MessagingHolder(View itemView) {
        super(itemView);
        message=(TextView)itemView.findViewById(R.id.message_id);
    }
}
