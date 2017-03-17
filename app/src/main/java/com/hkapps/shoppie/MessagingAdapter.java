package com.hkapps.shoppie;

import android.content.Context;
import android.view.Gravity;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

/**
 * Created by vamshi on 17-03-2017.
 */

public class MessagingAdapter extends FirebaseRecyclerAdapter<MessagingObject,MessagingHolder> {
    public static String TAG=MessagingAdapter.class.getSimpleName();
    private Context context;

    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public MessagingAdapter(Class<MessagingObject> modelClass, int modelLayout, Class<MessagingHolder> viewHolderClass, Query ref,Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context=context;
    }

    @Override
    protected void populateViewHolder(MessagingHolder viewHolder, MessagingObject model, int position) {
        viewHolder.message.setText(model.getMessage());
        String user=model.getSentBy().toString();
        String mainuser= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        if(user.equals(mainuser)){
            viewHolder.message.setGravity(Gravity.END);
        }
    }
}
