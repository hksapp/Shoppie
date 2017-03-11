package com.hkapps.shoppie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class profile extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    private TextView mailid,username;
    private ImageView imageview;
    private Uri filePath;
    private StorageReference mStorageRef;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         mailid =(TextView)findViewById(R.id.mailid);
        username=(TextView)findViewById(R.id.username);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final FirebaseDatabase database= FirebaseDatabase.getInstance();


        ref=database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
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
        putImageView();
    }
     @Override
     protected void onActivityResult(int requestCode,int resultCode,Intent data){
         super.onActivityResult(requestCode,resultCode,data);
         if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
             filePath=data.getData();
             try{
                 Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                 imageview.setImageBitmap(bitmap);
                 uploadFile();
             }catch(IOException e){
                    e.printStackTrace();
             }
         }
     }
    private void showFileChooser(){
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"select picture"),PICK_IMAGE_REQUEST);
    }
private void putImageView(){
    imageview =(ImageView)findViewById(R.id.userimage);
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            String url= dataSnapshot.child("userImageUrl").getValue().toString();
            Picasso.with(profile.this).load(url).fit().centerCrop().into(imageview);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    imageview.setOnClickListener(this);

}
private void uploadFile(){
    if(filePath!=null){
    final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        StorageReference userimage=mStorageRef.child("images/username.jpg");
        userimage.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //hiding the progress dialog
                progressDialog.dismiss();
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                ref.child("userImageUrl").setValue(downloadUrl.toString());
                //and displaying a success toast
                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

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

    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == imageview) {
            showFileChooser();
        }
       /* //if the clicked button is upload
        else if (view == buttonUpload) {

        }*/
    }
}
