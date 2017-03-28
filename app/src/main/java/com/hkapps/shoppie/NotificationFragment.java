package com.hkapps.shoppie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private RecyclerView notificationRecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private NotificationAdapter mNotificationAdapter;
    private DatabaseReference mDatabaseRef;
    private Query childRef;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        notificationRecyclerview = (RecyclerView) v.findViewById(R.id.notification_recycler_view);
            /*groceryRecyclerview.setHasFixedSize(true);*/
        childRef =  FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Notifications").orderByChild("live_shopping").equalTo(true);
        childRef.keepSynced(true);
        mNotificationAdapter = new NotificationAdapter(NotificationObject.class, R.layout.notification_ui, NotificationHolder.class, childRef, getContext());
        mNotificationAdapter.notifyDataSetChanged();
        notificationRecyclerview.setLayoutManager(linearLayoutManager);
        notificationRecyclerview.setAdapter(mNotificationAdapter);


        return v;
    }

}
