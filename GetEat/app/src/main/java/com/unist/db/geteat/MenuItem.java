package com.unist.db.geteat;

/**
 * Created by mintaewon on 2016. 6. 6..
 */
public class MenuItem {
    public String menu_category;
    public String menu_name;
    public String menu_price;
    public MenuItem(String menu_category, String menu_name, String menu_price){
        this.menu_category = menu_category;
        this.menu_name= menu_name;
        this.menu_price = menu_price;
    }
}
