package com.hkapps.shoppie;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Pranav on 28-03-2017.
 */

public class NotificationAdapter extends FirebaseRecyclerAdapter<NotificationObject, NotificationHolder> {
    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */

    private Context context;

    public NotificationAdapter(Class<NotificationObject> modelClass, int modelLayout, Class<NotificationHolder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final NotificationHolder viewHolder, final NotificationObject model, int position) {



        viewHolder.notif_txt.setText(model.getFriend_name()+" Went to Supermarket");

        DatabaseReference imgRef = FirebaseDatabase.getInstance().getReference().child(DetailGroceryList.getUserId()).child("userImageUrl");

        imgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Picasso.with(context).load(dataSnapshot.getValue().toString()).into(viewHolder.notif_img);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
