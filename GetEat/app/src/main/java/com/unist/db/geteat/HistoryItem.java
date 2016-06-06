package com.unist.db.geteat;

/**
 * Created by mintaewon on 2016. 6. 7..
 */
public class HistoryItem {
    public String history_id;
    public String resname;
    public String price;
    public String date;
    public HistoryItem(String history_id,String resname,String price, String date) {
        this.history_id = history_id;
        this.resname = resname;
        this.price = price;
        this.date = date;

    }
}
