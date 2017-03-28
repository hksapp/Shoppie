package com.hkapps.shoppie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Conversation extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView send_message, plusImage;
    private EditText messageTosend;
    private DatabaseReference mref, ref, ref_circle;
    private String messageid, fireBaseUser, currentUser, getusername;
    private String chatid = "";
    private Boolean ismine;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private LinearLayoutManager linearlayoutmanager;
    private RecyclerView recyclerView;
    private MessagingAdapter mAdapter;
    private StorageReference mStorageRef;
    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ismine = getIntent().getBooleanExtra("IsMyConversation", false);
        send_message = (ImageView) findViewById(R.id.send_message);
        plusImage = (ImageView) findViewById(R.id.plus);
        messageTosend = (EditText) findViewById(R.id.message_to_be_sent);
        mref = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        fireBaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(fireBaseUser);
        ref_circle = ref;
        if (!ismine) {
            chatid = MallItemsActivity.chatId;
        } else {
            chatid = fireBaseUser;
        }
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!messageTosend.getText().toString().equals("")) {
                    if (!ismine) {
                        messageid = mref.child("MallChat").child(chatid).push().getKey();
                    } else {
                        messageid = mref.child("MallChat").child(chatid).push().getKey();
                        ref_circle.child("Circle").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                                    currentUser = snapShot.getKey().toString();
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
                    mref.child("MallChat").child(chatid).child(messageid).child("messageType").setValue("text");
                    Toast.makeText(Conversation.this, "message sent", Toast.LENGTH_SHORT).show();
                    messageTosend.setText("");
                    linearlayoutmanager.setStackFromEnd(true);
                }
            }
        });
        plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageid = mref.child("MallChat").child(chatid).push().getKey();
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "select picture"), PICK_IMAGE_REQUEST);
            }
        });
        linearlayoutmanager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.messaging_recyclerView);
        /*recyclerView.setHasFixedSize(true);*/
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mDatabaseRef.child("MallChat").child(chatid);
        mAdapter = new MessagingAdapter(MessagingObject.class, R.layout.messaging_screen, MessagingHolder.class, childRef, getApplicationContext());
        mAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearlayoutmanager);
        recyclerView.setAdapter(mAdapter);
        linearlayoutmanager.setStackFromEnd(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            uploadFile();
        }
    }

    private void uploadFile() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            StorageReference userimage = mStorageRef.child("images/" + messageid + ".jpg");
            userimage.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //hiding the progress dialog
                    progressDialog.dismiss();
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    mref.child("MallChat").child(chatid).child(messageid).child("message").setValue(downloadUrl.toString());
                    mref.child("MallChat").child(chatid).child(messageid).child("messageType").setValue("image");
                    mref.child("MallChat").child(chatid).child(messageid).child("sentBy").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    //and displaying a success toast
                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                    linearlayoutmanager.setStackFromEnd(true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();

                    //and displaying error message
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    //displaying percentage in progress dialog
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");

                }
            });

        }
    }

}
