package com.tarashor.db;

import java.util.Date;

/**
 * Created by Taras on 05.01.2017.
 */
public class StatItem {
    private String pass;
    private Date date;
    private int count;

    public int getCount() {
        return count;
    }

    public Date getDate() {
        return date;
    }

    public String getPass() {
        return pass;
    }

    public static StatItem createStatItem(String pass, int count, Date date){
        StatItem statItem = new StatItem();
        statItem.count = count;
        statItem.pass = pass;
        statItem.date = date;
        return statItem;
    }
}
