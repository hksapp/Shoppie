package com.hkapps.shoppie;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * Created by kamal on 26-02-2017.
 */
public class GroceryAdapter extends FirebaseRecyclerAdapter<GroceryObject, GroceryHolder> {
    private static final String TAG = GroceryAdapter.class.getSimpleName();
    private Context context;
    DatabaseReference edtUpdateRef, deleteRef;
    private static String buffer="";
    private boolean firstTimeCheck = true;
    double longitude;
    double latitude;
    GpsTacker   gps;
    ProgressDialog pd;
    StringBuilder googlePlacesUrl,resultOfJson=new StringBuilder("");
    private static final String GOOGLE_API_KEY = "AIzaSyBcB22W221UdiT4Ij9yVy23t1EYIDmJPIU";
    private int PROXIMITY_RADIUS = 100;
    String type="food",finalResult;
    JSONObject res;
    JSONArray jsonArray;

    public GroceryAdapter(Class<GroceryObject> modelClass, int modelLayout, Class<GroceryHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final GroceryHolder viewHolder, GroceryObject model, int position) {
        // viewHolder.recipeName.setText(model.getRecipename());


        //   Glide.with(context).load(model.getRecipeImageUrl()).into(viewHolder.recipeImage);

        viewHolder.edt.setText(model.getItemname());


        viewHolder.chkbox.setChecked(model.isCheck());


        final String item_key = getRef(position).getKey().toString();

       /* FirebaseDatabase.getInstance().getReferenceFromUrl(DetailGroceryList.edtRef).child("items").child(item_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("sentby").exists())
                  buffer=dataSnapshot.child("sentby").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        int fromNotif = sharedPreference.getInt("from_notif", 100);


//fromNotif == false
        if (fromNotif == 2) {
            viewHolder.deleteIcon.setVisibility(View.VISIBLE);
            viewHolder.chkbox.setEnabled(true);
            viewHolder.deleteIcon.setEnabled(true);
            viewHolder.edt.setEnabled(true);
            if (item_key.length() > getUserId().length()/*!buffer.equals("")*/) {
                viewHolder.edt.setEnabled(false);
                viewHolder.edt.setTextColor(Color.BLUE);
/*
                Toast.makeText(context, model.getItemname(), Toast.LENGTH_SHORT).show();
*/
            }

        } else {
            viewHolder.edt.setEnabled(false);
            viewHolder.deleteIcon.setVisibility(View.GONE);
            viewHolder.chkbox.setEnabled(false);

            if (item_key.contains(getUserId())/*!buffer.equals("")*/) {

                viewHolder.edt.setEnabled(true);
                viewHolder.deleteIcon.setVisibility(View.VISIBLE);
                viewHolder.edt.setTextColor(Color.BLUE);

            }
        }
        viewHolder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!DetailGroceryList.edtRef.equals("")) {
                    deleteRef = FirebaseDatabase.getInstance().getReferenceFromUrl(DetailGroceryList.edtRef).child("items");

                } else {
                    deleteRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(DetailGroceryList.pushid).child("items");

                }

                deleteRef.child(item_key).removeValue();
                deleteRef.keepSynced(true);
            }
        });

        viewHolder.chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference chkboxRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(DetailGroceryList.pushid).child("items");


                chkboxRef.child(item_key).child("check").setValue(viewHolder.chkbox.isChecked());
                chkboxRef.keepSynced(true);
                gps = new GpsTacker(context);
                if (firstTimeCheck) {
                    if(gps.canGetLocation()){
                        longitude = gps.getLongitude();
                        latitude = gps .getLatitude();
                        /*Toast.makeText(context,"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude), Toast.LENGTH_SHORT).show();*/
                        googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                        googlePlacesUrl.append("location=" + latitude + "," + longitude);
                        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
                        googlePlacesUrl.append("&types=" + type);
                        googlePlacesUrl.append("&sensor=true");
                        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);
                        try {
                            String str=new JsonTask().execute(String.valueOf(googlePlacesUrl)).get();
                            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        gps.showSettingsAlert(view);
                    }
                    DatabaseReference notifRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("Circle");
                    notifRef.keepSynced(true);
                    notifRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot dp : dataSnapshot.getChildren()) {

                                DatabaseReference notifyTheUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(dp.getKey().toString()).child("Notifications");

                                notifyTheUserRef.keepSynced(true);
                                DatabaseReference nPath = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(DetailGroceryList.pushid);

                                Map postdata = new HashMap();
                                postdata.put("friend_user_id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                postdata.put("friend_name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString());
                                postdata.put("timestamp", ServerValue.TIMESTAMP);
                                postdata.put("seen", true);
                                postdata.put("current_list_id",DetailGroceryList.pushid);
                                postdata.put("list_ref", nPath.getRef().toString());
                                postdata.put("live_shopping",true);

                                notifyTheUserRef.push().setValue(postdata);


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }


                firstTimeCheck = false;


            }
        });


        viewHolder.edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (!b) {

                    if (!DetailGroceryList.edtRef.equals("")) {
                        edtUpdateRef = FirebaseDatabase.getInstance().getReferenceFromUrl(DetailGroceryList.edtRef).child("items");
                    } else {
                        edtUpdateRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child(PersonalGroceryList.ListCategory.toString()).child(DetailGroceryList.pushid).child("items");

                    }
                    edtUpdateRef.keepSynced(true);
                    edtUpdateRef.child(item_key).child("itemname").setValue(viewHolder.edt.getText().toString());
                }

            }
        });


    }

    private String getUserId() {

        return FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

    }

    private String getUserEmailId() {

        return FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

    }

    private String getUserDisplayName() {

        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();

    }



    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    resultOfJson.append(line);
                }
                try {
                    res=new JSONObject(resultOfJson.toString());
                    jsonArray =res.getJSONArray("results");
                    res=jsonArray.getJSONObject(0);
                    finalResult=res.getString("name");
                    /*Looper.prepare();*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return finalResult;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            /*txtJson.setText(result);*/
        }
    }
}