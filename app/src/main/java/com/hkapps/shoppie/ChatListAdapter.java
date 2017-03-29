package com.hkapps.shoppie;

        import android.content.Context;
        import android.content.Intent;
        import android.view.View;

        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.firebase.database.Query;

/**
 * Created by vamshi on 16-03-2017.
 */

public class ChatListAdapter extends FirebaseRecyclerAdapter<ChatListObject,ChatListHolder> {
    private static String TAG=CircleAdapter.class.getSimpleName();
    private Context context;

    /**
     * @param modelClass      Firebase will marshall the data at a location into an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list. You will be responsible for populating an
     *                        instance of the corresponding view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                        combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    public ChatListAdapter(Class<ChatListObject> modelClass, int modelLayout, Class<ChatListHolder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context=context;
    }

    @Override
    protected void populateViewHolder(ChatListHolder viewHolder, ChatListObject model, final int position) {
        viewHolder.username.setText(model.getUsername());
      /* if(model.getUserImageUrl()!=null) {
            Picasso.with(context).load(model.getUserImageUrl().toString()).into(viewHolder.userImageUrl);
       }*/
        viewHolder.chatid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MallItemsActivity.chatId=getRef(position).getKey().toString();
                Intent i = new Intent(context,Conversation.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("IsMyConversation",false);
                context.startActivity(i);
            }
        });
    }
}
