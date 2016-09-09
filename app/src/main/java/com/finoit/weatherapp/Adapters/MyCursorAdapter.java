package com.finoit.weatherapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finoit.weatherapp.DatabaseHelper.WeatherDatabaseContract;
import com.finoit.weatherapp.R;

/**
 * Created by emp269 on 9/9/2016.
 */
public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_main,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String day = cursor.getString(cursor.getColumnIndex(WeatherDatabaseContract.WeatherEntry.Day));
        String maxtemp = cursor.getString(cursor.getColumnIndex(WeatherDatabaseContract.WeatherEntry.MaxTemp));
        String mintemp = cursor.getString(cursor.getColumnIndex(WeatherDatabaseContract.WeatherEntry.MinTemp));
        String icon = cursor.getString(cursor.getColumnIndex(WeatherDatabaseContract.WeatherEntry.Icon));
        TextView daytext = (TextView)view.findViewById(R.id.daytext);
        TextView maxtemptext = (TextView)view.findViewById(R.id.maxtemptext);
        TextView mintemptext = (TextView)view.findViewById(R.id.mintemptext);
        ImageView iconview = (ImageView)view.findViewById(R.id.iconview);
        daytext.setText(day);
        maxtemptext.setText(maxtemp);
        mintemptext.setText(mintemp);
        int iconid = context.getResources().getIdentifier("drawable/a"+icon,null,context.getPackageName());
        Drawable iconimage = context.getResources().getDrawable(iconid);
        iconview.setImageDrawable(iconimage);

    }
}
