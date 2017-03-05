package com.hkapps.shoppie;

import android.content.Context;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kamal on 26-02-2017.
 */
public class GroceryAdapter extends FirebaseRecyclerAdapter<GroceryObject, GroceryHolder> {
    private static final String TAG = GroceryAdapter.class.getSimpleName();
    private Context context;
    public GroceryAdapter(Class<GroceryObject> modelClass, int modelLayout, Class<GroceryHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }
    @Override
    protected void populateViewHolder(final GroceryHolder viewHolder, GroceryObject model, int position) {
       // viewHolder.recipeName.setText(model.getRecipename());


        //   Glide.with(context).load(model.getRecipeImageUrl()).into(viewHolder.recipeImage);

        viewHolder.edt.setText(model.getItemname());

        final String item_key = getRef(position).getKey().toString();

viewHolder.edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View view, boolean b) {

        if(!b){

            DatabaseReference edtUpdateRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("List").child(DetailGroceryList.pushid).child("items");

            edtUpdateRef.child(item_key).child("itemname").setValue(viewHolder.edt.getText().toString());
        }

    }
});

    }

    private String getUserId(){

        return FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

    }

    private String getUserEmailId(){

        return FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

    }

    private String getUserDisplayName(){

        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();

    }
}