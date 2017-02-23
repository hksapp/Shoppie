package com.hkapps.shoppie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class GroceryList extends AppCompatActivity {

    private TextView create_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);




        create_list = (TextView) findViewById(R.id.create_list);

        OpenDetailGroceryList();



    }

    private void OpenDetailGroceryList(){

        create_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(GroceryList.this,DetailGroceryList.class);
                startActivity(i);
            }
        });
    }
}
