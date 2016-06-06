package com.unist.db.geteat;

/**
 * Created by mintaewon on 2016. 6. 6..
 */
public class OrderListItem {
    public String menu_name;
    public String menu_number;
    public OrderListItem(String menu_name, String number){
        this.menu_name= menu_name;
        this.menu_number = number;
    }
}
