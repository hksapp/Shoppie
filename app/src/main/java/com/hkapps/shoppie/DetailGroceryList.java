package com.hkapps.shoppie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailGroceryList extends AppCompatActivity {

    private RecyclerView groceryRecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private GroceryAdapter mGroceryAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    public static String pushid;
    private DatabaseReference listRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_grocery_list);

        Button add_item = (Button) findViewById(R.id.add_item);
        final EditText title = (EditText) findViewById(R.id.groceries_list_title);



         listRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("List");

        pushid = listRef.push().getKey();



        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){

                    listRef.child(pushid).child("title").setValue(title.getText().toString());
                }
            }
        });


        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                listRef.child(pushid).child("items").push().child("itemname").setValue("Item name ");


                mGroceryAdapter.notifyDataSetChanged();


            }
        });


        linearLayoutManager = new LinearLayoutManager(this);
        groceryRecyclerview = (RecyclerView) findViewById(R.id.grocery_recycler_view);
        groceryRecyclerview.setHasFixedSize(true);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mDatabaseRef.child("Users").child(getUserId()).child("List").child(pushid).child("items");
        mGroceryAdapter = new GroceryAdapter(GroceryObject.class, R.layout.grocery_ui, GroceryHolder.class, childRef, getApplicationContext());
        groceryRecyclerview.setLayoutManager(linearLayoutManager);
        groceryRecyclerview.setAdapter(mGroceryAdapter);

        Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();

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
