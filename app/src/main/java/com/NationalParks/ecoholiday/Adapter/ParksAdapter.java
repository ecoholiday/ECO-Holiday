package com.NationalParks.ecoholiday.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.NationalParks.ecoholiday.Activity.DistanceActivity;
import com.NationalParks.ecoholiday.Activity.Home;
import com.NationalParks.ecoholiday.Activity.Home;
import com.NationalParks.ecoholiday.Activity.LandingPage;
import com.NationalParks.ecoholiday.Activity.MapsActivity;
import com.NationalParks.ecoholiday.Activity.ParkActivity;
import com.NationalParks.ecoholiday.Activity.PlanNationalParkActivity;
import com.NationalParks.ecoholiday.Item.ParkItems;
import com.NationalParks.ecoholiday.R;

import java.util.ArrayList;

public class ParksAdapter extends BaseAdapter {

    Context context;
    private ArrayList<ParkItems> parksList;
    LayoutInflater inflter;
    TextView nationalParkName,area,distance;
    ImageButton park_Navigation;

    SharedPreferences sharedpreferences;

    public ParksAdapter(Context applicationContext, ArrayList<ParkItems> parks) {
        sharedpreferences = applicationContext.getSharedPreferences(Home.MyPREFERENCES, Context.MODE_PRIVATE);

        this.context = applicationContext;
        this.parksList = parks;
        inflter = (LayoutInflater.from(applicationContext));

    }

    @Override
    public int getCount() {
        return parksList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.list_parks, null);
        nationalParkName = (TextView) convertView.findViewById(R.id.NParkName);
        area = (TextView) convertView.findViewById(R.id.Area);
        distance = (TextView) convertView.findViewById(R.id.ParkDistance);
        park_Navigation = (ImageButton) convertView.findViewById(R.id.btnNavigation);

        final ParkItems item = parksList.get(position);

        nationalParkName.setText(item.getNationalParks());
        area.setText("Area (ft2) : "+item.getArea());
        distance.setText("Distance (km) : "+item.getDistance());

        park_Navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ParkLatitude",item.getLatitude());
                editor.putString("ParkLongitude", item.getLongitude());
                editor.putString("ParkName",item.getNationalParks());
                editor.putString("ParkArea",item.getArea());
                editor.putString("ParkDistance",item.getDistance()+"");
                editor.commit();
                context.startActivity(new Intent(context,MapsActivity.class));
            }
        });

        nationalParkName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ParkLatitude",item.getLatitude());
                editor.putString("ParkLongitude", item.getLongitude());
                editor.putString("ParkName",item.getNationalParks());
                editor.putString("ParkArea",item.getArea());
                editor.putString("ParkDistance",item.getDistance()+"");
                editor.putInt("NPID",item.getNPID());
                editor.commit();
                context.startActivity(new Intent(context, ParkActivity.class));
            }
        });

        return convertView;
    }
}
