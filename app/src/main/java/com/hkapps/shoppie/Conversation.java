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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Conversation extends AppCompatActivity {
    private ImageView send_message;
    private EditText messageTosend;
    private DatabaseReference mref;
    private String chatid,messageid;
    private Boolean isnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        isnew = getIntent().getBooleanExtra("IsNewConversation",false);
        send_message = (ImageView)findViewById(R.id.send_message);
        messageTosend = (EditText)findViewById(R.id.message_to_be_sent);
        if(!isnew){
            chatid=MallItemsActivity.chatId;
        }
        mref= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("MallItems");
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chatid==null)
                {chatid=mref.push().getKey();}
                messageid=mref.child(chatid).push().getKey();
                mref.child(chatid).child("createdBy").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                mref.child(chatid).child("messages").child(messageid).child("userId").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                mref.child(chatid).child("messages").child(messageid).child("message").setValue(messageTosend.getText().toString());
                Toast.makeText(Conversation.this, "message sent", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
