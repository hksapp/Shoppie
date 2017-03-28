package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Pranav on 28-03-2017.
 */

public class NotificationHolder extends RecyclerView.ViewHolder {

    public TextView notif_txt;
    public ImageView notif_img;
    public LinearLayout notif_layout;

    public NotificationHolder(View itemView) {
        super(itemView);

        notif_img = (ImageView) itemView.findViewById(R.id.notif_img);

        notif_txt = (TextView) itemView.findViewById(R.id.notif_txt);

        notif_layout = (LinearLayout) itemView.findViewById(R.id.notif_layout);
    }
}
