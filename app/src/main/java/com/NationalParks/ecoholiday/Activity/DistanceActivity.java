package com.NationalParks.ecoholiday.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.NationalParks.ecoholiday.Adapter.ParksAdapter;
import com.NationalParks.ecoholiday.Item.ParkItems;
import com.NationalParks.ecoholiday.R;
import com.google.android.gms.nearby.messages.Distance;


import java.util.ArrayList;

public class DistanceActivity extends AppCompatActivity {
    SQLiteDatabase mDatabase;
    TextView distanceCount;
    TextView dayCount;
    ImageView imgSearch;
    SeekBar seekDistance;
    SeekBar seekDays;
    ListView viewData;
    int DistCount=1000;
    int DaysCount=5;
    SharedPreferences sharedpreferences;
    private ProgressDialog pDialog;
    public static ArrayList<ParkItems> ParksList ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        // Toolbar changes

        ImageButton imgExplore = (ImageButton) findViewById(R.id.imgExplore);
        imgExplore.setImageResource(R.mipmap.explore_clicked);

        final ImageButton imgStats = (ImageButton) findViewById(R.id.imgStats);
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExplore = new Intent(DistanceActivity.this, Statistics.class);
                startActivity(intentExplore);

            }
        });

        final ImageButton imgHelp = (ImageButton) findViewById(R.id.imgHelp);
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgHelp = new Intent(DistanceActivity.this, Help.class);
                startActivity(imgHelp);

            }
        });

        final ImageButton imgChecklist = (ImageButton) findViewById(R.id.imgChecklist);
        imgChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgChecklist = new Intent(DistanceActivity.this, CheckList.class);
                startActivity(imgChecklist);

            }
        });

        // Toolbar changes

        setTitle("Distance");

        sharedpreferences = getSharedPreferences(PlanHoliday.MyPREFERENCES, Context.MODE_PRIVATE);

        distanceCount = (TextView) findViewById(R.id.count);
        dayCount = (TextView) findViewById(R.id.day_count);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        seekDistance = (SeekBar) findViewById(R.id.seek_distance);
        seekDays = (SeekBar) findViewById(R.id.seek_days);
        viewData = (ListView) findViewById(R.id.view_list);

        ParksList = new ArrayList<ParkItems>();

        mDatabase = openOrCreateDatabase(PlanHoliday.DATABASE_NAME, MODE_PRIVATE, null);

        new GetParksData().execute(); //adding to get the list upon opening the page - charithesh


        seekDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceCount.setText(progress+"");
                DistCount = progress;
                //
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //new GetParksData().execute();

            }

        });

        seekDays.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dayCount.setText(progress+"");
                DaysCount = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //new GetParksData().execute();
            }
        });


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetParksData().execute();
            }
        });



    }

    private class GetParksData extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DistanceActivity.this);
            pDialog.setMessage("Data Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            ParksList.clear();
        }

        @Override
        protected String doInBackground(String... params) {

            String z = "SUCCESS";
            int newArea = 0;

            if(DaysCount == 1){newArea = 8000;}
            else if(DaysCount == 2){newArea = 18000;}
            else if (DaysCount == 3){newArea = 21000;}
            else if (DaysCount == 4){newArea = 28000;}
            else {newArea = 700000;}

            try{

                String query = "select * from tbl_NationalParksList where Distance <="+DistCount+" and CAST(Area as int) <= "+newArea+" order by Distance ASC";
                Cursor cursorRoom = mDatabase.rawQuery(query, null);

                if(cursorRoom.moveToFirst()){
                    do{
                        final ParkItems item = new ParkItems();
                        item.setNationalParks(cursorRoom.getString(cursorRoom.getColumnIndex("NationalPark")));
                        item.setArea(cursorRoom.getString(cursorRoom.getColumnIndex("Area")));
                        item.setLatitude(cursorRoom.getString(cursorRoom.getColumnIndex("Latitude")));
                        item.setLongitude(cursorRoom.getString(cursorRoom.getColumnIndex("Longitude")));
                        item.setDistance(cursorRoom.getInt(cursorRoom.getColumnIndex("Distance")));
                        item.setNPID(cursorRoom.getInt(cursorRoom.getColumnIndex("NPID")));
                        ParksList.add(item);
                    }while (cursorRoom.moveToNext());
                }
                cursorRoom.close();
            }catch (Exception e){
                z="FAIL";
            }
            return z;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.hide();

            if(result.equals("SUCCESS")){
                if(ParksList.size()>0){
                    ParksAdapter parkListAdapter = new ParksAdapter(DistanceActivity.this,ParksList);
                    viewData.setAdapter(parkListAdapter);
                    viewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                            ParkItems parkListItem = ParksList.get(position);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("ParkLatitude",parkListItem.getLatitude());
                            editor.putString("ParkLongitude", parkListItem.getLongitude());
                            editor.putString("ParkName",parkListItem.getNationalParks());
                            editor.putString("ParkArea",parkListItem.getArea());
                            editor.putString("ParkDistance",parkListItem.getDistance()+"");
                            editor.commit();
                            startActivity(new Intent(DistanceActivity.this,MapsActivity.class));
                        }
                    });

                }else{
                    viewData.setAdapter(null);
                    Toast.makeText(getApplicationContext(),"No Parks Found to this Distance",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
