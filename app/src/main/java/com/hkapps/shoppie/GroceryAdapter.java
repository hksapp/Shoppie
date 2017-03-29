package com.hkapps.shoppie;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.preference.PreferenceManager;
        import android.view.View;
        import android.widget.Toast;

        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ServerValue;
        import com.google.firebase.database.ValueEventListener;

        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by kamal on 26-02-2017.
 */
public class GroceryAdapter extends FirebaseRecyclerAdapter<GroceryObject, GroceryHolder> {
    private static final String TAG = GroceryAdapter.class.getSimpleName();
    private Context context;
    DatabaseReference edtUpdateRef, deleteRef;
    private static String buffer="";
    private boolean firstTimeCheck = true;

    public GroceryAdapter(Class<GroceryObject> modelClass, int modelLayout, Class<GroceryHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final GroceryHolder viewHolder, GroceryObject model, int position) {
        // viewHolder.recipeName.setText(model.getRecipename());


        //   Glide.with(context).load(model.getRecipeImageUrl()).into(viewHolder.recipeImage);

        viewHolder.edt.setText(model.getItemname());


        viewHolder.chkbox.setChecked(model.isCheck());


        final String item_key = getRef(position).getKey().toString();

       /* FirebaseDatabase.getInstance().getReferenceFromUrl(DetailGroceryList.edtRef).child("items").child(item_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("sentby").exists())
                  buffer=dataSnapshot.child("sentby").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int fromNotif = sharedPreference.getInt("from_notif", 100);


//fromNotif == false
        if (fromNotif == 2) {
            viewHolder.deleteIcon.setVisibility(View.VISIBLE);
            viewHolder.chkbox.setEnabled(true);
            viewHolder.edt.setEnabled(true);
            if (item_key.length() > getUserId().length()/*!buffer.equals("")*/) {
                viewHolder.edt.setEnabled(false);
                viewHolder.edt.setTextColor(Color.BLUE);
                Toast.makeText(context, model.getItemname(), Toast.LENGTH_SHORT).show();
            }

        } else {
            viewHolder.edt.setEnabled(false);
            viewHolder.deleteIcon.setVisibility(View.GONE);
            viewHolder.chkbox.setEnabled(false);

            if (item_key.contains(getUserId())/*!buffer.equals("")*/) {

                viewHolder.edt.setEnabled(true);
                viewHolder.deleteIcon.setVisibility(View.VISIBLE);
                viewHolder.edt.setTextColor(Color.BLUE);

            }
        }
        viewHolder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!DetailGroceryList.edtRef.equals("")) {
                    deleteRef = FirebaseDatabase.getInstance().getReferenceFromUrl(DetailGroceryList.edtRef).child("items");

                } else {
                    deleteRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(DetailGroceryList.pushid).child("items");

                }

                deleteRef.child(item_key).removeValue();
                deleteRef.keepSynced(true);
            }
        });

        viewHolder.chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference chkboxRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(DetailGroceryList.pushid).child("items");


                chkboxRef.child(item_key).child("check").setValue(viewHolder.chkbox.isChecked());
                chkboxRef.keepSynced(true);
                if (firstTimeCheck) {

                    DatabaseReference notifRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("Circle");
                    notifRef.keepSynced(true);
                    notifRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot dp : dataSnapshot.getChildren()) {

                                DatabaseReference notifyTheUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(dp.getKey().toString()).child("Notifications");

                                notifyTheUserRef.keepSynced(true);
                                DatabaseReference nPath = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(DetailGroceryList.pushid);

                                Map postdata = new HashMap();
                                postdata.put("friend_user_id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                postdata.put("friend_name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString());
                                postdata.put("timestamp", ServerValue.TIMESTAMP);
                                postdata.put("seen", true);
                                postdata.put("current_list_id",DetailGroceryList.pushid);
                                postdata.put("list_ref", nPath.getRef().toString());
                                postdata.put("live_shopping",true);

                                notifyTheUserRef.push().setValue(postdata);


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }


                firstTimeCheck = false;


            }
        });


        viewHolder.edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (!b) {

                    if (!DetailGroceryList.edtRef.equals("")) {
                        edtUpdateRef = FirebaseDatabase.getInstance().getReferenceFromUrl(DetailGroceryList.edtRef).child("items");
                    } else {
                        edtUpdateRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(DetailGroceryList.pushid).child("items");

                    }
                    edtUpdateRef.keepSynced(true);
                    edtUpdateRef.child(item_key).child("itemname").setValue(viewHolder.edt.getText().toString());
                }

            }
        });


    }

    private String getUserId() {

        return FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

    }

    private String getUserEmailId() {

        return FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

    }

    private String getUserDisplayName() {

        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();

    }
}