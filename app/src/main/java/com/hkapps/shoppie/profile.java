package com.hkapps.shoppie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    private TextView mailid,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         mailid =(TextView)findViewById(R.id.mailid);
        username=(TextView)findViewById(R.id.username);
        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mailid.setText(dataSnapshot.child("email").getValue().toString());
                username.setText(dataSnapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
