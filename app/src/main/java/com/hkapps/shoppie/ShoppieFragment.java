package com.hkapps.shoppie;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppieFragment extends Fragment {

    private Button groceries_button;

    public ShoppieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_one, container, false);


         groceries_button = (Button) rootview.findViewById(R.id.groceries_button);


        OpenGroceryList();





        return rootview;
    }


    private void OpenGroceryList(){

        groceries_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),GroceryList.class);
                startActivity(i);

            }
        });
    }

}
