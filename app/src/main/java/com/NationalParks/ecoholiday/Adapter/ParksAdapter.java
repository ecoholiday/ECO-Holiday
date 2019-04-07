package com.NationalParks.ecoholiday.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.NationalParks.ecoholiday.Item.ParkItems;
import com.NationalParks.ecoholiday.R;

import java.util.ArrayList;

public class ParksAdapter extends BaseAdapter {

    Context context;
    private ArrayList<ParkItems> parksList;
    LayoutInflater inflter;
    TextView nationalParkName,area,distance;
    ImageView park_Image;

    public ParksAdapter(Context applicationContext, ArrayList<ParkItems> parks) {

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
//        park_Image = (ImageView)convertView.findViewById(R.id.parkImage);

        final ParkItems item = parksList.get(position);

        nationalParkName.setText(item.getNationalParks());
        area.setText("Area (ft2) : "+item.getArea());
        distance.setText("Distance (km) : "+item.getDistance());

//        if(item.getParkImage() != null){
//            byte[] decodedString = Base64.decode(item.getAssetImage(), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            park_Image.setImageBitmap(decodedByte);
//        }

        return convertView;
    }
}
