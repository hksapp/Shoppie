package com.hkapps.shoppie;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppieFragment extends Fragment {

    private Button groceries_button,medicines_button,mallitems_button;

    public ShoppieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_shoppie, container, false);


        groceries_button = (Button) rootview.findViewById(R.id.groceries_button);
        medicines_button = (Button) rootview.findViewById(R.id.medicines_button);
        mallitems_button = (Button) rootview.findViewById(R.id.mallitems_button);

        OpenGroceryList();





        return rootview;
    }


    private void OpenGroceryList(){

        groceries_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),PersonalGroceryList.class);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("source", 1);
                edit.commit();
                startActivity(i);

            }
        });

        medicines_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PersonalGroceryList.class);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("source", 2);
                edit.commit();
                startActivity(i);

            }
        });

        mallitems_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),MallItemsActivity.class);
                startActivity(i);
            }
        });
    }

}
