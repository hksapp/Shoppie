package com.hkapps.shoppie;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
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

public class profile extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    private TextView mailid, username, noofmembers;
    private LinearLayout circlecountview;
    private ImageView imageview;
    private Uri filePath;
    private Button signout;
    private StorageReference mStorageRef;
    private DatabaseReference ref, ref2;
    /* boolean isImageFitToScreen=false;*/
    private int circleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Profile");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mailid = (TextView) findViewById(R.id.mailid);
        username = (TextView) findViewById(R.id.username);

        signout = (Button) findViewById(R.id.signout);


        noofmembers = (TextView) findViewById(R.id.circleCount);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        circlecountview=(LinearLayout)findViewById(R.id.circlecountclick);
        circlecountview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(profile.this,Circle.class);
                startActivity(i);
            }
        });
        ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
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
        ref2 = ref.child("Circle");


        noofmembers.setText(String.valueOf(circleCount));


        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                noofmembers.setText(String.valueOf(dataSnapshot.getChildrenCount()));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      /*  // create bitmap from resource
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.splash4);

        // set Circle bitmap
        ImageView mImage = (ImageView) findViewById(R.id.userimage);
        mImage.setImageBitmap(getCircleBitmap(bm));*/
        putImageView();
        Switch s = (Switch) findViewById(R.id.location_switch);
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        int myIntValue = sp.getInt("location_preference", -1);
        if (myIntValue == 1) {
            s.setChecked(true);
        } else if (myIntValue == 0) {
            s.setChecked(false);
        }
        s.setOnCheckedChangeListener(profile.this);


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(profile.this);
                getApplication().stopService(new Intent(getApplication(), NotificationListener.class));
                finish();
                }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageview.setImageBitmap(bitmap);
                uploadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "select picture"), PICK_IMAGE_REQUEST);
    }

    private void putImageView() {
        imageview = (ImageView) findViewById(R.id.userImageUrl);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("userImageUrl").exists()) {
                    String url = dataSnapshot.child("userImageUrl").getValue().toString();
                    Picasso.with(profile.this).load(url).fit().centerCrop().into(imageview);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        imageview.setOnClickListener(this);
        imageview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showFileChooser();
                return false;
            }
        });
    }

    /*  private Bitmap getCircleBitmap(Bitmap bitmap) {
          final Bitmap jsonArray = Bitmap.createBitmap(bitmap.getWidth(),
                  bitmap.getHeight(), Bitmap.Config.ARGB_8888);
          final Canvas canvas = new Canvas(jsonArray);

          final int color = Color.RED;
          final Paint paint = new Paint();
          final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
          final RectF rectF = new RectF(rect);

          paint.setAntiAlias(true);
          canvas.drawARGB(0, 0, 0, 0);
          paint.setColor(color);
          canvas.drawOval(rectF, paint);

          paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
          canvas.drawBitmap(bitmap, rect, rect, paint);

          bitmap.recycle();

          return jsonArray;
      }*/
    private void uploadFile() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            StorageReference userimage = mStorageRef.child("images/" + DetailGroceryList.getUserId().toString() + ".jpg");
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
           /* if(isImageFitToScreen) {
                isImageFitToScreen=false;
                imageview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                imageview.setAdjustViewBounds(true);
            }else{
                isImageFitToScreen=true;
                imageview.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT));
                imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            }*/
        }
       /* //if the clicked button is upload
        else if (view == buttonUpload) {

        }*/
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int isenabled;
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (b) {
            isenabled = 1;

        } else {
            isenabled = 0;
        }
        editor.putInt("location_preference", isenabled);
        editor.commit();


    }
}
