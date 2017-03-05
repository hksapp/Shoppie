package com.hkapps.shoppie;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by kamal on 26-02-2017.
 */

public class GroceryHolder extends RecyclerView.ViewHolder{
    private static final String TAG = GroceryHolder.class.getSimpleName();
    public EditText edt;
    public ImageView recipeImage;
    public GroceryHolder(View itemView) {
        super(itemView);

edt = (EditText) itemView.findViewById(R.id.edt);
    }
}