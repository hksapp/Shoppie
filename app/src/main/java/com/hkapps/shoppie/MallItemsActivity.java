package com.hkapps.shoppie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MallItemsActivity extends AppCompatActivity {
    private TextView myconv;
    private DatabaseReference ref;
    static String chatId/*,time,chatkey*/;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mDatabaseRef,childRef/*,timeRef*/;
    private RecyclerView recyclerView;
   /* private long cutoff,oldtime;*/
    private ChatListAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Mall Items");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_items);
       /* cutoff = new Date().getTime();*/
        myconv = (TextView)findViewById(R.id.yourconversation);
        myconv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MallItemsActivity.this,Conversation.class);
                i.putExtra("IsMyConversation",true);
                startActivity(i);
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.chats_recyclerView);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mDatabaseRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("ActiveChats");
        chatAdapter = new ChatListAdapter(ChatListObject.class, R.layout.activity_chat_list, ChatListHolder.class, childRef, getApplicationContext());
        chatAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatAdapter);
       /* timeRef=FirebaseDatabase.getInstance().getReference();
        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(snapshot.exists()){
                    chatkey=snapshot.getKey().toString();
                    timeRef=mDatabaseRef.child("MallChat").child(chatkey).child("timestamp");
                    timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                            time=dataSnapshot.getValue().toString();
                                oldtime=Long.parseLong(time);
                            if(cutoff-oldtime>5*60*1000)
                            {
                                mDatabaseRef.child("MallChat").child(chatkey).removeValue();
                            }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatAdapter.cleanup();
    }
}
