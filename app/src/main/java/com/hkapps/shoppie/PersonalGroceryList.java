package com.hkapps.shoppie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.hkapps.shoppie.DetailGroceryList.getUserId;

public class PersonalGroceryList extends AppCompatActivity {

    private TextView create_list,line;
    private RecyclerView listRecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private PersonalListAdapter pListAdapter;
    private DatabaseReference mListDatabaseRef , childRef;
    public static String ListCategory = "dupp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int grocery_or_medicine = sharedPreference.getInt("source", 100);
        if(grocery_or_medicine==1){
            ListCategory="List";
            setTitle("Grocery List");
        }
        else if(grocery_or_medicine==2){
            ListCategory="Medicinelist";
            setTitle("Medicine List");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        create_list = (TextView) findViewById(R.id.create_list);
        OpenDetailGroceryList();
        linearLayoutManager = new LinearLayoutManager(this);
        listRecyclerview = (RecyclerView) findViewById(R.id.list_recycler_view);
        listRecyclerview.setHasFixedSize(true);
        mListDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mListDatabaseRef.child("Users").child(getUserId()).child(ListCategory);
        pListAdapter = new PersonalListAdapter(PersonalGroceryObject.class, R.layout.personal_grocery_ui, PersonalGroceryHolder.class, childRef, getApplicationContext());
       listRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        listRecyclerview.setAdapter(pListAdapter);



    }

    private void OpenDetailGroceryList(){

        create_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(PersonalGroceryList.this,DetailGroceryList.class);
                startActivity(i);
            }
        });
    }
}
