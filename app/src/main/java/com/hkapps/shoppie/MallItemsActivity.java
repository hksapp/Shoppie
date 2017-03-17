package com.hkapps.shoppie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MallItemsActivity extends AppCompatActivity {
    private TextView myconv;
    private DatabaseReference ref;
    static String chatId;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private RecyclerView recyclerView;
    private ChatListAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_items);
/*
        ref= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("MallItems");
*/
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
        recyclerView.setHasFixedSize(true);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mDatabaseRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("ActiveChats");
        chatAdapter = new ChatListAdapter(ChatListObject.class, R.layout.activity_chat_list, ChatListHolder.class, childRef, getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatAdapter);
    }
}
