package com.example.quakequack;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView=convertView;
        if(rootView == null){
            rootView= LayoutInflater.from(getContext()).inflate(R.layout.layoutz,parent,false);
        }
        Earthquake currentp=getItem(position);
        double dm=Double.parseDouble(currentp.getMmagnitude());
        int magniColor=getColor(dm);

        TextView textView=(TextView)rootView.findViewById(R.id.magnitude);
        textView.setText(currentp.getMmagnitude());
        GradientDrawable gradientDrawable=(GradientDrawable)textView.getBackground();
        gradientDrawable.setColor(magniColor);
        TextView textView1=(TextView)rootView.findViewById(R.id.location1);
        textView1.setText(currentp.getMplace1());
        TextView textView5=(TextView)rootView.findViewById(R.id.location2);
        textView5.setText(currentp.getMplace2());
        TextView textView2=(TextView)rootView.findViewById(R.id.date);
        textView2.setText(currentp.getMdate());
        TextView textView4=(TextView)rootView.findViewById(R.id.time);
        textView4.setText(currentp.getMTime());
        return rootView;

    }

    private int getColor(double dm) {
        int colorId;
        int magnif=(int)Math.floor(dm);
        switch(magnif){
            case 0:
            case 1:
                colorId=R.color.magnitude1;
                break;
            case 2:
                colorId=R.color.magnitude2;
                break;
            case 3:
                colorId=R.color.magnitude3;
                break;
            case 4:
                colorId=R.color.magnitude4;
                break;
            case 5:
                colorId=R.color.magnitude5;
                break;
            case 6:
                colorId=R.color.magnitude6;
                break;
            case 7:
                colorId=R.color.magnitude7;
                break;
            case 8:
                colorId=R.color.magnitude8;
                break;
            case 9:
                colorId=R.color.magnitude9;
                break;
            default:
                colorId=R.color.magnitude10plus;

        }
        return ContextCompat.getColor(getContext(),colorId);

    }
}
