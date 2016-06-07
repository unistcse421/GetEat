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
    public String res_id;
    public String score;
    public String num_people;






    public ResListItem(String imgurl, String name, String cuisine, String location, String phone_num, String start, String end, String Delivery_Fee, String Delivery_Min,String res_id, String score,String num_people){
        this.res_imgurl = imgurl;
        this.res_name = name;
        this.res_cuisine = cuisine;
        this.res_start = start;
        this.res_end = end;
        this.res_fee = Delivery_Fee;
        this.res_min = Delivery_Min;
        this.res_location = location;
        this.res_phone_num = phone_num;
        this.res_id = res_id;
        this.score = score;
        this.num_people = num_people;
    }
}
