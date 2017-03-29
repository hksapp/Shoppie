package com.hkapps.shoppie;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailGroceryList extends AppCompatActivity {

    private RecyclerView groceryRecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private GroceryAdapter mGroceryAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    public static String pushid;
    private DatabaseReference listRef;
    private long itemcount;

    public static String edtRef ="";


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(getIntent().getStringExtra("list_ref")==null){

            DatabaseReference notifRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("Circle");
            notifRef.keepSynced(true);
            notifRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot dp : dataSnapshot.getChildren()) {

                        final DatabaseReference notifyTheUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(dp.getKey().toString()).child("Notifications");

                        notifyTheUserRef.orderByChild("current_list_id").equalTo(DetailGroceryList.pushid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    notifyTheUserRef.child(dsp.getKey()).child("live_shopping").setValue(false);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_grocery_list);



        Button add_item = (Button) findViewById(R.id.add_item);
        Button done_shopping = (Button) findViewById(R.id.done_shopping);
        final EditText title = (EditText) findViewById(R.id.groceries_list_title);

//Handling When it Comes from notification!
        if(getIntent().getStringExtra("list_ref")!=null){

            edtRef = getIntent().getStringExtra("list_ref").toString();

done_shopping.setVisibility(View.GONE);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt("from_notif", 1);
            edit.commit();

            final DatabaseReference notificatonRef = FirebaseDatabase.getInstance().getReferenceFromUrl(getIntent().getStringExtra("list_ref").toString());


            title.setEnabled(false);


            notificatonRef.child("title").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        title.setText(dataSnapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            add_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String buffer=notificatonRef.push().getKey().toString();

                    notificatonRef.child("items").child(getUserId()+"_"+buffer).child("itemname").setValue("");
                    notificatonRef.child("items").child(getUserId()+"_"+buffer).child("sentby").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    mGroceryAdapter.notifyDataSetChanged();


                }
            });



            linearLayoutManager = new LinearLayoutManager(this);
            groceryRecyclerview = (RecyclerView) findViewById(R.id.grocery_recycler_view);
            /*groceryRecyclerview.setHasFixedSize(true);*/
            childRef = notificatonRef.child("items");
            childRef.keepSynced(true);
            mGroceryAdapter = new GroceryAdapter(GroceryObject.class, R.layout.grocery_ui, GroceryHolder.class, childRef, getApplicationContext());
            mGroceryAdapter.notifyDataSetChanged();
            groceryRecyclerview.setLayoutManager(linearLayoutManager);
            groceryRecyclerview.setAdapter(mGroceryAdapter);










        } else {
if(getIntent().getIntExtra("new_list",0)==3) {
    done_shopping.setVisibility(View.GONE);
}else {
    done_shopping.setVisibility(View.VISIBLE);
}

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt("from_notif", 2);
            edit.commit();

            listRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString());

            if (getIntent().getStringExtra("list_id") != null) {
                pushid = getIntent().getStringExtra("list_id").toString();

            } else {
                pushid = listRef.push().getKey();

            }


            title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {

                        listRef.child(pushid).child("title").setValue(title.getText().toString());
                    }
                }
            });

            listRef.child(pushid).child("title").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        title.setText(dataSnapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            add_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    listRef.child(pushid).child("items").push().child("itemname").setValue("");


                    mGroceryAdapter.notifyDataSetChanged();
                    hideSoftKeyboard(DetailGroceryList.this, view);

                }
            });


            done_shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    DatabaseReference notifRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("Circle");
                    notifRef.keepSynced(true);
                    notifRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot dp : dataSnapshot.getChildren()) {

                                final DatabaseReference notifyTheUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(dp.getKey().toString()).child("Notifications");

                                notifyTheUserRef.orderByChild("current_list_id").equalTo(DetailGroceryList.pushid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                            notifyTheUserRef.child(dsp.getKey()).child("live_shopping").setValue(false);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




finish();

            }


            });



            linearLayoutManager = new LinearLayoutManager(this);
            groceryRecyclerview = (RecyclerView) findViewById(R.id.grocery_recycler_view);
            /*groceryRecyclerview.setHasFixedSize(true);*/
            mDatabaseRef = FirebaseDatabase.getInstance().getReference();
            childRef = mDatabaseRef.child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(pushid).child("items");
            childRef.keepSynced(true);
            mGroceryAdapter = new GroceryAdapter(GroceryObject.class, R.layout.grocery_ui, GroceryHolder.class, childRef, getApplicationContext());
            groceryRecyclerview.setLayoutManager(linearLayoutManager);
            groceryRecyclerview.setAdapter(mGroceryAdapter);

            Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();

        }

    }

    public static String getUserId(){

        return FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

    }

    public static String getUserEmailId(){

        return FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

    }

    public static String getUserDisplayName(){

        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();

    }
    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
