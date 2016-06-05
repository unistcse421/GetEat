package com.unist.db.geteat;

import java.util.ArrayList;

/**
 * Created by mintaewon on 2015. 7. 24..
 */
public class ResListItem extends ArrayList<ResListItem> {
    public String res_name;
    public String res_imgurl;
    public String res_location;
    public String res_start;
    public String res_end;
    public String res_fee;
    public String res_min;
    public String res_cuisine;
    public String res_phone_num;






    public ResListItem(String imgurl, String name, String cuisine, String location, String phone_num, String start, String end, String Delivery_Fee, String Delivery_Min){
        this.res_imgurl = imgurl;
        this.res_name = name;
        this.res_cuisine = cuisine;
        this.res_start = start;
        this.res_end = end;
        this.res_fee = Delivery_Fee;
        this.res_min = Delivery_Min;
        this.res_location = location;
        this.res_phone_num = phone_num;
    }
}
