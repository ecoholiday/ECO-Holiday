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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.NationalParks.ecoholiday.Adapter.ParksAdapter;
import com.NationalParks.ecoholiday.Item.ParkItems;
import com.NationalParks.ecoholiday.R;


import java.util.ArrayList;

public class DistanceActivity extends AppCompatActivity {
    SQLiteDatabase mDatabase;
    TextView distanceCount;
    ImageView imgSearch;
    SeekBar seekDistance;
    ListView viewData;
    int DistCount=1000;
    SharedPreferences sharedpreferences;
    private ProgressDialog pDialog;
    public static ArrayList<ParkItems> ParksList ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Distance");

        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        distanceCount = (TextView) findViewById(R.id.count);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        seekDistance = (SeekBar) findViewById(R.id.seek_distance);
        viewData = (ListView) findViewById(R.id.view_list);

        ParksList = new ArrayList<ParkItems>();

        mDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

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
                new GetParksData().execute();

            }

        });

       /* imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetParksData().execute();
            }
        }); */



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

            try{

                String query = "select * from tbl_NationalParksList where Distance <="+DistCount+" order by Distance ASC";
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
