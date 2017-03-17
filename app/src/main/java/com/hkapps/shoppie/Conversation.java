package com.hkapps.shoppie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Conversation extends AppCompatActivity {
    private ImageView send_message;
    private EditText messageTosend;
    private DatabaseReference mref,ref,ref_circle;
    private String chatid,messageid, fireBaseUser,currentUser,getusername;
    private Boolean ismine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ismine = getIntent().getBooleanExtra("IsMyConversation",false);
        send_message = (ImageView)findViewById(R.id.send_message);
        messageTosend = (EditText)findViewById(R.id.message_to_be_sent);
        Toast.makeText(Conversation.this, ismine.toString(), Toast.LENGTH_SHORT).show();
        mref= FirebaseDatabase.getInstance().getReference();
        fireBaseUser =FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        ref=FirebaseDatabase.getInstance().getReference().child("Users").child(fireBaseUser);
        ref_circle=ref;
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ismine){
                    chatid=MallItemsActivity.chatId;
                    messageid=mref.child("MallChat").child(chatid).push().getKey();
                }
                else {
                    chatid=fireBaseUser;
                    messageid=mref.child("MallChat").child(chatid).push().getKey();
                    ref_circle.child("Circle").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapShot:dataSnapshot.getChildren()){
                                currentUser=snapShot.getKey().toString();
                                mref.child("Users").child(currentUser).child("ActiveChats").child(fireBaseUser).child("username").setValue(DetailGroceryList.getUserDisplayName());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                mref.child("MallChat").child(chatid).child(messageid).child("message").setValue(messageTosend.getText().toString());
                mref.child("MallChat").child(chatid).child(messageid).child("sentBy").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                Toast.makeText(Conversation.this, "message sent", Toast.LENGTH_SHORT).show();
            }
        });

    }
}