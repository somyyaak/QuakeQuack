package com.example.quakequack;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {
    private double mmagnitude;
    private String mplace;
    private String muri;
    private long mdate;
    private Date date;
    public Earthquake(double magnitude, String place, long time, String uri){
        mmagnitude=magnitude;
        mdate=time;
        mplace=place;
        muri=uri;
        date=new Date(mdate);
    }
    public String getMmagnitude(){
        DecimalFormat format=new DecimalFormat("0.0");
        return format.format(mmagnitude);
    }
    public String getMplace1(){
        if(mplace.contains("of")) {
            String[] z=mplace.split("of");
            return z[0]+" of";
        }
        return "Near the";
    }
    public String getMplace2(){

        if(mplace.contains("of")){
            String[] z=mplace.split("of");
            return z[1];
        }
        return mplace;
    }
    public String getMuri(){
        return muri;
    }
    public String getMdate(){

        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(date);
    }
    public String getMTime(){

        SimpleDateFormat dateFormat=new SimpleDateFormat("h:mm a");
        return dateFormat.format(date);
    }
}

