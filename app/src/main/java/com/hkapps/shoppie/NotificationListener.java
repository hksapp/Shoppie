package com.hkapps.shoppie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.hkapps.shoppie.DetailGroceryList.getUserId;

/**
 * Created by Pranav on 16-03-2017.
 */

public class NotificationListener extends Service {


    private NotificationCompat.Builder mBuilder;
    private int notif_id = 0;
    private static int i=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {


        DatabaseReference nRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("Notifications");




        nRef.orderByChild("seen").equalTo(true).limitToFirst(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



               /* if (notif_id > 30000)
                    notif_id = 0;

                notif_id = notif_id + 1;
*/

                if(dataSnapshot.child("seen").exists()) {
              showNotifications(dataSnapshot.child("friend_name").getValue().toString(), dataSnapshot.child("location").getValue().toString(), dataSnapshot.child("list_ref").getValue().toString());
              dataSnapshot.child("seen").getRef().removeValue();
           }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        DatabaseReference mallRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("MallNotifications");


        mallRef.orderByChild("seen").equalTo(true).limitToFirst(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



               /* if (notif_id > 30000)
                    notif_id = 0;

                notif_id = notif_id + 1;
*/

                if(dataSnapshot.child("seen").exists()) {
                    MallItemsActivity.chatId = dataSnapshot.child("friend_user_id").getValue().toString();
                    showMallNotifications(dataSnapshot.child("friend_name").getValue().toString());

                    dataSnapshot.child("seen").getRef().removeValue();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        return START_STICKY;
    }

    private void showNotifications(String username,  String reacted,String list_ref) {

        mBuilder = new NotificationCompat.Builder(this);


        // mBuilder.setLargeIcon(Picasso.with(getBaseContext()).load(pic).get());

        mBuilder.setContentTitle(username+" is at ");
        mBuilder.setContentText(reacted);
        mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_light_focused);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.circle_24dp);
        mBuilder.setLargeIcon(icon);
        //  Toast.makeText(this, pic, Toast.LENGTH_SHORT).show();
       // loadImage(getApplicationContext(), pic);

     /*   if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.drawable.bestfrnds);
        } else {
           mBuilder.setSmallIcon(R.drawable.bestfrnds);
        }*/


        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        Intent resultIntent = new Intent(this, DetailGroceryList.class);
        resultIntent.putExtra("list_ref", list_ref);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DetailGroceryList.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
        // PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);

        mBuilder.setContentIntent(resultPendingIntent);


        mBuilder.setVibrate(new long[]{500, 500});

       /* Notification note = mBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;
*/

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(notif_id, mBuilder.build());

       /* final int notifId = 1337;
        final RemoteViews contentView = mBuilder.getContentView();
        final int iconId = android.R.id.icon;
        Picasso.with(getApplicationContext()).load(pic).into(contentView, iconId, notifId,mBuilder.build() );
*/



    }











    private void showMallNotifications(String username) {

        mBuilder = new NotificationCompat.Builder(this);


        // mBuilder.setLargeIcon(Picasso.with(getBaseContext()).load(pic).get());

        mBuilder.setContentTitle(username);
        mBuilder.setContentText(username + " " + "Group is Active");
        mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_light_focused);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.circle_24dp);
        mBuilder.setLargeIcon(icon);
        //  Toast.makeText(this, pic, Toast.LENGTH_SHORT).show();
        // loadImage(getApplicationContext(), pic);

     /*   if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.drawable.bestfrnds);
        } else {
           mBuilder.setSmallIcon(R.drawable.bestfrnds);
        }*/


        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);



        Intent resultIntent = new Intent(this, Conversation.class);
        resultIntent.putExtra("IsMyConversation",false);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Conversation.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
        // PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);

        mBuilder.setContentIntent(resultPendingIntent);


        mBuilder.setVibrate(new long[]{500, 500});

       /* Notification note = mBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;
*/

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(notif_id, mBuilder.build());

       /* final int notifId = 1337;
        final RemoteViews contentView = mBuilder.getContentView();
        final int iconId = android.R.id.icon;
        Picasso.with(getApplicationContext()).load(pic).into(contentView, iconId, notifId,mBuilder.build() );
*/



    }


}
