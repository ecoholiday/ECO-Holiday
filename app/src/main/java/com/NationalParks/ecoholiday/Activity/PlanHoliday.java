package com.NationalParks.ecoholiday.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.NationalParks.ecoholiday.R;
import com.google.android.gms.games.stats.Stats;

public class PlanHoliday extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_holiday);


        ImageView imgNationalPark = (ImageView)findViewById(R.id.imgNationalPark);
        imgNationalPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNP = new Intent(PlanHoliday.this, MainActivity.class);
                startActivity(intentNP);
            }
        });

        ImageView imgStats = (ImageView)findViewById(R.id.imgStats);
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStats = new Intent(PlanHoliday.this, Statistics.class);
                startActivity(intentStats);
            }
        });

        ImageView imgHiking = (ImageView)findViewById(R.id.imgHiking);
        imgHiking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStats = new Intent(PlanHoliday.this, Cycling.class);
                startActivity(intentStats);
            }
        });
        ImageView imgCycling = (ImageView)findViewById(R.id.imgCycling);
        imgCycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStats = new Intent(PlanHoliday.this, Cycling.class);
                startActivity(intentStats);
            }
        });

    }
}
