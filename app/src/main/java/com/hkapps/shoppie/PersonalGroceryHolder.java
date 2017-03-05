package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kamal on 06-03-2017.
 */
public class PersonalGroceryHolder extends RecyclerView.ViewHolder {

    public TextView personal_title;

    public PersonalGroceryHolder(View itemView) {
        super(itemView);

        personal_title = (TextView) itemView.findViewById(R.id.personal_title);
    }


}
