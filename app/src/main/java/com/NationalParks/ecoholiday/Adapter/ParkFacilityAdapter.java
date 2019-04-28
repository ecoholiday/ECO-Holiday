package com.NationalParks.ecoholiday.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.NationalParks.ecoholiday.Item.ParkFacilityListItem;

import com.NationalParks.ecoholiday.R;

import java.util.ArrayList;

public class ParkFacilityAdapter extends BaseAdapter {
    Context context;
    private ArrayList<ParkFacilityListItem> parksfacilityList;
    LayoutInflater inflter;
    TextView parkFacilityName;

    public ParkFacilityAdapter(Context applicationContext, ArrayList<ParkFacilityListItem> parksList) {
        //parksfacilityList.clear();
        this.context = applicationContext;
        this.parksfacilityList = parksList;
        inflter = (LayoutInflater.from(applicationContext));

    }

    @Override
    public int getCount() {
        return parksfacilityList.size();
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
        convertView = inflter.inflate(R.layout.list_parkfacility, null);
        parkFacilityName = (TextView) convertView.findViewById(R.id.NParkFacilityName);


        final ParkFacilityListItem item = parksfacilityList.get(position);

        parkFacilityName.setText(item.getParkFacilityName());

        return convertView;
    }
}
