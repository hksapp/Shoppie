package com.hkapps.shoppie;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kamal on 06-03-2017.
 */
public class PersonalListAdapter extends FirebaseRecyclerAdapter<PersonalGroceryObject, PersonalGroceryHolder> {
    private Context context;

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
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final PersonalGroceryHolder viewHolder, PersonalGroceryObject model, int position) {


        viewHolder.personal_title.setText(model.getTitle());

        final String list_id = getRef(position).getKey().toString();

        viewHolder.Plist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, DetailGroceryList.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("list_id", list_id);
                context.startActivity(i);
            }
        });




        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(DetailGroceryList.getUserId()).child("List").child(list_id).child("items");
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("item0").exists()) {
                    viewHolder.item0.setText(dataSnapshot.child("item0").child("itemname").getValue().toString());

                    if (dataSnapshot.child("item1").exists()) {

                        viewHolder.item1.setText(dataSnapshot.child("item1").child("itemname").getValue().toString());

                        if (dataSnapshot.child("item2").exists()) {

                            viewHolder.item2.setText(dataSnapshot.child("item2").child("itemname").getValue().toString());


                        }


                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        viewHolder.Plist.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


              final AlertDialog.Builder alert = new AlertDialog.Builder(
                        context);
                alert.setTitle("Confirm Deletion");
                alert.setMessage("Are you sure to delete List");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        DatabaseReference deleteRef = FirebaseDatabase.getInstance().getReference().child("Users").child(DetailGroceryList.getUserId()).child("List").child(list_id);

                        deleteRef.removeValue();

                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

            alert.show();

                return true;
            }
        });



    }


}
