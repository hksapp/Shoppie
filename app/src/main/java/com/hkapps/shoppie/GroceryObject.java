package com.hkapps.shoppie;

/**
 * Created by kamal on 26-02-2017.
 */

public class GroceryObject {

    private String itemname;
    private boolean check;

    public GroceryObject(){


    }

    public GroceryObject(String itemname,boolean check){

        this.itemname  = itemname;
        this.check = check;
    }

    public String getItemname() {return itemname;}

    public void setItemname(String itemname) {this.itemname = itemname;}

    public boolean isCheck(){ return check; }

    public void setCheck(boolean check) {this.check = check;}
}
