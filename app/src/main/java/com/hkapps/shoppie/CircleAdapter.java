package com.hkapps.shoppie;

import android.content.Context;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.hkapps.shoppie.DetailGroceryList.getUserId;

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
        final String item_key = getRef(position).getKey().toString();
        viewHolder.mailId.setText(model.getEmail());
        Picasso.with(context).load(model.getUserImageUrl().toString()).into(viewHolder.userimage);
        viewHolder.removeUserFromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deleteRef = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserId()).child("Circle");
                deleteRef.child(item_key).removeValue();
            }
        });
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
        Picasso.with(v.getContext()).load(circle.getUserImageUrl()).fit().centerCrop().into(holder.userimage);
        holder.username.setText(circle.getUsername());
        holder.mailid.setText(circle.getEmail());
    }

    @Override
    public int getItemCount(){
        return circleList.size();
    }*/
}
