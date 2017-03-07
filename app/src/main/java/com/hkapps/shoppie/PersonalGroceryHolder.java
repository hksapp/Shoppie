package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kamal on 06-03-2017.
 */
public class PersonalGroceryHolder extends RecyclerView.ViewHolder {

    public TextView personal_title;
    public LinearLayout Plist;
    public TextView item0;
    public TextView item1;
    public TextView item2;

    public PersonalGroceryHolder(View itemView) {
        super(itemView);

        personal_title = (TextView) itemView.findViewById(R.id.personal_title);
        item0 = (TextView) itemView.findViewById(R.id.item0);
        item1 = (TextView) itemView.findViewById(R.id.item1);
        item2 = (TextView) itemView.findViewById(R.id.item2);
        Plist = (LinearLayout) itemView.findViewById(R.id.Plist);

    }


}
