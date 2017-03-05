package com.hkapps.shoppie;

/**
 * Created by kamal on 26-02-2017.
 */

public class GroceryObject {

    private String itemname;

    public GroceryObject(){


    }

    public GroceryObject(String itemname){

        this.itemname  = itemname;
    }

    public String getItemname() {return itemname;}

    public void setItemname(String itemname) {this.itemname = itemname;}
}
