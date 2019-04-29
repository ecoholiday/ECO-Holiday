package com.NationalParks.ecoholiday.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.NationalParks.ecoholiday.R;

public class report extends AppCompatActivity {
    String sdate;
    String edate;
    String distance;
    double diffDays;
    double moneySaved;
    double carbonSaved;

    TextView count1,day_count1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        Intent fromParkActivity = getIntent();
        sdate = fromParkActivity.getStringExtra("startDate");
        edate = fromParkActivity.getStringExtra("endDate");
        distance = fromParkActivity.getStringExtra("distance");
        distance = distance.substring(15);


        diffDays = 3;
        moneySaved = 5.9*(diffDays) - Integer.parseInt(distance)*0.42;
        if (moneySaved<0){
            moneySaved = moneySaved*(-1);
        }
        moneySaved = Math.round(moneySaved * 100.0) / 100.0;
        carbonSaved = (8115*diffDays) - 195.49*Integer.parseInt(distance);
        carbonSaved = Math.round(carbonSaved * 100.0) / 100.0;
        count1 = (TextView)findViewById(R.id.count1);
        day_count1 = (TextView)findViewById(R.id.day_count1);

        count1.setText(String.valueOf(moneySaved));
        day_count1.setText(String.valueOf(carbonSaved));



    }
}
