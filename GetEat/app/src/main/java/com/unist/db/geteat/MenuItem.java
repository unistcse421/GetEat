package com.unist.db.geteat;

/**
 * Created by mintaewon on 2016. 6. 6..
 */
public class MenuItem {
    public String menu_category;
    public String menu_name;
    public String menu_price;
    public String menu_id;
    public MenuItem(String menu_category, String menu_name, String menu_price, String menu_id){
        this.menu_category = menu_category;
        this.menu_name= menu_name;
        this.menu_price = menu_price;
        this.menu_id = menu_id;
    }
}
