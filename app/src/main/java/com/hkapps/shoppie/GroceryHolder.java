package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by kamal on 26-02-2017.
 */

public class GroceryHolder extends RecyclerView.ViewHolder{
    private static final String TAG = GroceryHolder.class.getSimpleName();
    public EditText edt;
    public CheckBox chkbox;
    public ImageView deleteIcon;
    public GroceryHolder(View itemView) {
        super(itemView);

edt = (EditText) itemView.findViewById(R.id.edt);
        deleteIcon = (ImageView) itemView.findViewById(R.id.deleteIcon);
        chkbox = (CheckBox) itemView.findViewById(R.id.chkbox);

    }
}