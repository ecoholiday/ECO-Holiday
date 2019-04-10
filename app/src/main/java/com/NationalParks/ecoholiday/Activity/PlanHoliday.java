package com.NationalParks.ecoholiday.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.os.Handler;

import com.NationalParks.ecoholiday.R;
import com.google.android.gms.games.stats.Stats;

public class PlanHoliday extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_holiday);


        final ImageView imgNationalPark = (ImageView)findViewById(R.id.imgNationalPark);
        imgNationalPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgNationalPark.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);
                imgNationalPark.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentNP = new Intent(PlanHoliday.this, MainActivity.class);
                startActivity(intentNP);
            }
        });

        final ImageView imgStats = (ImageView)findViewById(R.id.imgStats);
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgStats.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);
                imgStats.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentStats = new Intent(PlanHoliday.this, Statistics.class);
                startActivity(intentStats);
            }
        });


        final ImageView imgHiking = (ImageView)findViewById(R.id.imgHiking);
        imgHiking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgHiking.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);
                imgHiking.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentHik = new Intent(PlanHoliday.this, Cycling.class);
                startActivity(intentHik);
            }
        });

        final ImageView imgCycling = (ImageView)findViewById(R.id.imgCycling);
        imgCycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgCycling.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);
                imgCycling.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentCyc = new Intent(PlanHoliday.this, Cycling.class);
                startActivity(intentCyc);
            }
        });

        final ImageView imgCamping = (ImageView)findViewById(R.id.imgCamping);
        imgCamping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgCamping.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);

                imgCamping.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentCamp = new Intent(PlanHoliday.this, Cycling.class);
                startActivity(intentCamp);
            }
        });
    }

}
