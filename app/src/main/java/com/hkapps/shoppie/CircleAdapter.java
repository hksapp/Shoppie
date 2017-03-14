package com.hkapps.shoppie;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vamshi on 14-03-2017.
 */

public class CircleAdapter extends FirebaseRecyclerAdapter<CircleObject,CircleHolder> {
    private static String TAG=CircleAdapter.class.getSimpleName();
    private Context context;
    public CircleAdapter(Class<CircleObject> modelClass, int modelLayout, Class<CircleHolder> viewHoderClass, DatabaseReference ref,Context context){
        super(modelClass,modelLayout,viewHoderClass,ref);
        this.context=context;
    }

    @Override
    protected void populateViewHolder(CircleHolder viewHolder, CircleObject model, int position) {
        viewHolder.userName.setText(model.getUsername());
        viewHolder.mailId.setText(model.getGmail());
        Picasso.with(context).load(model.getUserimage()).fit().centerCrop().into(viewHolder.userimage);
    }
    /*
    private List<CircleObject> circleList;
    private View v;
    public class MyCircleHolder extends RecyclerView.ViewHolder{
        public TextView username,mailid;
        public ImageView userimage;
        public MyCircleHolder(View view){
            super(view);
            v=view;
            username=(TextView) view.findViewById(R.id.username_incirclelist);
            mailid=(TextView) view.findViewById(R.id.gmail_id_incircle_list);
            userimage=(ImageView)view.findViewById(R.id.userimage_incirclelist);
        }
    }
    public CircleAdapter(List<CircleObject> circleList){
        this.circleList=circleList;
    }

    @Override
    public MyCircleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_circle_list,parent,false);
        return new MyCircleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyCircleHolder holder,int position){
        CircleObject circle= circleList.get(position);
        Picasso.with(v.getContext()).load(circle.getUserimage()).fit().centerCrop().into(holder.userimage);
        holder.username.setText(circle.getUsername());
        holder.mailid.setText(circle.getGmail());
    }

    @Override
    public int getItemCount(){
        return circleList.size();
    }*/
}
