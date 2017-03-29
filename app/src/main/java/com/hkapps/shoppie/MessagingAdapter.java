package com.hkapps.shoppie;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by vamshi on 17-03-2017.
 */

public class MessagingAdapter extends FirebaseRecyclerAdapter<MessagingObject, MessagingHolder> {
    public static String TAG = MessagingAdapter.class.getSimpleName();
    private Context context;
    private DatabaseReference ref;
    private static String username;

    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public MessagingAdapter(Class<MessagingObject> modelClass, int modelLayout, Class<MessagingHolder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(MessagingHolder viewHolder, MessagingObject model, int position) {
        String user = model.getSentBy();
        String mainuser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        String mtype = model.getMessageType().toString();

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(model.getSentBy());
        ref.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        viewHolder.sentby_username.setText(username);
        if (mtype.equals("text")) {
            viewHolder.message.setText(model.getMessage());
            viewHolder.image.setVisibility(View.GONE);
            viewHolder.message.setVisibility(View.VISIBLE);
            if (user.equals(mainuser)) {
                viewHolder.message_layout.setGravity(Gravity.END);
                viewHolder.message_layout.setPadding(0, 0, 25, 0);
            } else {
                viewHolder.message_layout.setPadding(25, 0, 0, 0);
            }
        }
        if (mtype.equals("image")) {
            viewHolder.message.setVisibility(View.GONE);
            viewHolder.image.setVisibility(View.VISIBLE);
            Picasso.with(context).load(model.getMessage()).into(viewHolder.image);
            if (user.equals(mainuser)) {
               /* LinearLayout.LayoutParams params;
                params = (LinearLayout.LayoutParams) viewHolder.message_layout.getLayoutParams();
                params.gravity = Gravity.RIGHT;*/
                viewHolder.message_layout.setPadding(0, 0, 25, 0);
            } else
            {
                viewHolder.message_layout.setPadding(25, 0, 0, 0);
            }
        }

    }
}
