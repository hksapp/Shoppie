package com.hkapps.shoppie;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Circle extends AppCompatActivity {
    FirebaseDatabase database;
    private DatabaseReference ref,ref2;
    private String gmail,currentUserGmail;
    private EditText gmailid_circle;
    private static int value=0;
    private List<CircleObject> circleList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CircleAdapter cAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        setTitle("Add or remove members");

        database= FirebaseDatabase.getInstance();
        gmailid_circle=(EditText)findViewById(R.id.gmailid_circle);
        currentUserGmail=FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        ref=database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Circle");
        TextView adduser=(TextView)findViewById(R.id.add_user);
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gmail=gmailid_circle.getText().toString();
                database=FirebaseDatabase.getInstance();
                ref2=database.getReference().child("Users");
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            if(gmail.equals(snapshot.child("email").getValue().toString()))
                            {
                                if(gmail.equals(currentUserGmail))
                                    Toast.makeText(Circle.this, "You cannot add yourself to your circle", Toast.LENGTH_SHORT).show();
                                else{
                                ref.child(snapshot.getKey()).child("username").setValue(snapshot.child("username").getValue());
                                ref.child(snapshot.getKey()).child("email").setValue(snapshot.child("email").getValue());
                                ref.child(snapshot.getKey()).child("userImageUrl").setValue(snapshot.child("userImageUrl").getValue());

                                Toast.makeText(Circle.this, "added successfully", Toast.LENGTH_SHORT).show();}
                                value=1;
                            }

                        }
                        if(value!=1)
                        {
                            Toast.makeText(Circle.this, "invalid mailid/not exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                hideSoftKeyboard(Circle.this, view);
            }
        });


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.showuser_incircle);
        /*recyclerView.setHasFixedSize(true);*/
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mDatabaseRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Circle");
        cAdapter = new CircleAdapter(CircleObject.class, R.layout.activity_circle_list, CircleHolder.class, childRef, getApplicationContext());
        cAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cAdapter);
        /*prepareCircleData();*/
        Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
    }
    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

}