package com.hkapps.shoppie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class circle extends AppCompatActivity {
    FirebaseDatabase database;
    private DatabaseReference ref,ref2;
    private String gmail;
    private EditText gmailid_circle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        database= FirebaseDatabase.getInstance();
        gmailid_circle=(EditText)findViewById(R.id.gmailid_circle);

        ref=database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("circle");
        TextView adduser=(TextView)findViewById(R.id.add_user);
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmail=gmailid_circle.getText().toString();
                /*database=FirebaseDatabase.getInstance();
                ref2=database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            if(snapshot.child("email").getValue().toString().equals(gmail))
                            {    ref.child(gmail).child("username").setValue("vamshi krishna");}
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
                ref.child(gmail).child("username").setValue("vamshi krishna");
                Toast.makeText(circle.this, gmail, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
