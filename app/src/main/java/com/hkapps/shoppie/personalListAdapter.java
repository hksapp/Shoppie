package com.hkapps.shoppie;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by kamal on 06-03-2017.
 */
public class PersonalListAdapter extends FirebaseRecyclerAdapter<PersonalGroceryObject,PersonalGroceryHolder> {
    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.


     */
    public PersonalListAdapter(Class<PersonalGroceryObject> modelClass, int modelLayout, Class<PersonalGroceryHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        //this.context = context;
    }

    @Override
    protected void populateViewHolder(PersonalGroceryHolder viewHolder, PersonalGroceryObject model, int position) {


        viewHolder.personal_title.setText(model.getTitle());


    }



}
