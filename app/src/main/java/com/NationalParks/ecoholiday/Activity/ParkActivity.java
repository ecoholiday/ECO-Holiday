package com.NationalParks.ecoholiday.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.NationalParks.ecoholiday.Adapter.ParkFacilityAdapter;
import com.NationalParks.ecoholiday.Item.ParkFacilityListItem;
import com.NationalParks.ecoholiday.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ParkActivity extends AppCompatActivity {
    SQLiteDatabase mDatabase;
    Button btnCamping,btnTrekking,btnReport;
    TextView cDate,cWeather;
    SharedPreferences sharedpreferences;
    int NPID;
    String parkName;
    ProgressDialog pDialog;
    ArrayList<String> trekking = new ArrayList<String>();
    ParkFacilityAdapter parkFacilityAdapter;
    public static ArrayList<ParkFacilityListItem> ParkCampingFacilityList,ParkTrekkingFacilityList ;
    ListView TrekkingData,CampingData;
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String dateTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camping_trekking);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDatabase = openOrCreateDatabase(Home.DATABASE_NAME, MODE_PRIVATE, null);

        btnCamping = (Button) findViewById(R.id.btnCamping);
        btnTrekking = (Button) findViewById(R.id.btnTrekking);
        btnReport = (Button) findViewById(R.id.btnReport);
        cDate = (TextView) findViewById(R.id.current_date);
        cWeather = (TextView) findViewById(R.id.weatherCount);


        sharedpreferences = getSharedPreferences(Home.MyPREFERENCES, Context.MODE_PRIVATE);
        NPID = sharedpreferences.getInt("NPID", 0);
        parkName = sharedpreferences.getString("ParkName","");

        Calendar cal = Calendar.getInstance();
        dateTime = sdf.format(cal.getTime());
        cDate.setText(dateTime);
        cWeather.setText(30+"");
        setTitle(parkName);

        ParkCampingFacilityList = new ArrayList<ParkFacilityListItem>();
        ParkTrekkingFacilityList = new ArrayList<ParkFacilityListItem>();

        new GetParksFacilityData().execute();

        btnCamping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ParkCampingFacilityList.size()>0) {
                    final Dialog dialog1 = new Dialog(ParkActivity.this);
                    dialog1.setContentView(R.layout.custom_dailog);
                    dialog1.setTitle(parkName);
                    CampingData = (ListView) dialog1.findViewById(R.id.List);
                    parkFacilityAdapter = new ParkFacilityAdapter(ParkActivity.this, ParkCampingFacilityList);
                    CampingData.setAdapter(parkFacilityAdapter);
                    dialog1.show();
                }else{
                    Toast.makeText(getApplicationContext(),"Camping Data Not Available to this Park",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnTrekking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ParkTrekkingFacilityList.size()>0) {
                    final Dialog dialog = new Dialog(ParkActivity.this);
                    dialog.setContentView(R.layout.custom_dailog);
                    dialog.setTitle(parkName);
                    TrekkingData = (ListView) dialog.findViewById(R.id.List);
                    parkFacilityAdapter = new ParkFacilityAdapter(ParkActivity.this, ParkTrekkingFacilityList);
                    TrekkingData.setAdapter(parkFacilityAdapter);
                    dialog.show();
                }else{
                    Toast.makeText(getApplicationContext(),"Trekking Data Not Available to this Park",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(PlanNationalParkActivity.this,UnderDevelopmentActivity.class));
            }
        });

    }

    private class GetParksFacilityData extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ParkActivity.this);
            pDialog.setMessage("Data Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            ParkCampingFacilityList.clear();
            ParkTrekkingFacilityList.clear();
        }

        @Override
        protected String doInBackground(String... params) {

            String z = "SUCCESS";

            try{

                String query1 = "select * from tbl_Camping where NPID ="+NPID+" order by CampingDescription ASC";
                Cursor cursorCamping = mDatabase.rawQuery(query1, null);

                if(cursorCamping.moveToFirst()){
                    do{
                        final ParkFacilityListItem item1 = new ParkFacilityListItem();
                        item1.setParkFacilityName(cursorCamping.getString(cursorCamping.getColumnIndex("CampingDescription")));
                        item1.setParkFacilityID(cursorCamping.getInt(cursorCamping.getColumnIndex("CampID")));
                        ParkCampingFacilityList.add(item1);
                    }while (cursorCamping.moveToNext());
                }

                cursorCamping.close();
            }catch (Exception e){
                z="FAIL";
            }
            try{

                String query = "select * from tbl_Treck_Trail where NPID ="+NPID+" order by TrackName ASC";
                Cursor cursorTrekking = mDatabase.rawQuery(query, null);

                if(cursorTrekking.moveToFirst()){
                    do{
//                        trekking.add(cursorTrekking.getString(cursorTrekking.getColumnIndex("TrackName")));
                        final ParkFacilityListItem item = new ParkFacilityListItem();
                        item.setParkFacilityName(cursorTrekking.getString(cursorTrekking.getColumnIndex("TrackName")));
                        item.setParkFacilityID(cursorTrekking.getInt(cursorTrekking.getColumnIndex("TID")));
                        ParkTrekkingFacilityList.add(item);
                    }while (cursorTrekking.moveToNext());
                }
                cursorTrekking.close();
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

            }else{
                Toast.makeText(getApplicationContext(),"Loading Problem",Toast.LENGTH_LONG).show();
            }
        }
    }}
