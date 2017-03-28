package com.hkapps.shoppie;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Circle extends AppCompatActivity {
    FirebaseDatabase database;
    private DatabaseReference ref,ref2;
    private String gmail;
    private EditText gmailid_circle;
    private static int value=0;
    private List<CircleObject> circleList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CircleAdapter cAdapter;
    private CircleObject circle;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        database= FirebaseDatabase.getInstance();
        gmailid_circle=(EditText)findViewById(R.id.gmailid_circle);
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
                                ref.child(snapshot.getKey()).child("username").setValue(snapshot.child("username").getValue());
                                ref.child(snapshot.getKey()).child("email").setValue(snapshot.child("email").getValue());
                                ref.child(snapshot.getKey()).child("userImageUrl").setValue(snapshot.child("userImageUrl").getValue());
                                value=1;
                                Toast.makeText(Circle.this, "added successfully", Toast.LENGTH_SHORT).show();
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
       /* recyclerView = (RecyclerView) findViewById(R.id.showuser_incircle);

        cAdapter = new CircleAdapter(circleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);*/

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
/*
    private void prepareCircleData(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username,mailId,imageUrl;
                if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Toast.makeText(Circle.this, snapshot.child("username").getValue().toString(), Toast.LENGTH_SHORT).show();
                    username = snapshot.child("username").getValue().toString();
                    mailId = snapshot.child("email").getValue().toString();
                    imageUrl = snapshot.child("userImageUrl").getValue().toString();
                    circle = new CircleObject(username, mailId,imageUrl);
                    circleList.add(circle);
                 }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        cAdapter.notifyDataSetChanged();
    }*/
}