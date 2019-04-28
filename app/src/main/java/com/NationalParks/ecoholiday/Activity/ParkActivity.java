package com.NationalParks.ecoholiday.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ParkActivity extends AppCompatActivity implements  View.OnClickListener{

    SQLiteDatabase mDatabase;
    CheckBox chkCamp,chkTrek;
    Button btnReport;
    TextView cDate,cWeather;
    ListView lstActivity;

    //calendar
    private Calendar mcalendar;
    private EditText txtStart,txtEnd;
    private int day,month,year;

    //calendar

    Boolean firstTimeLoading = true;
    SharedPreferences sharedpreferences;
    int NPID;
    String parkName;
    String area;
    String parkDistance;
    String latitude;
    String longitude;
    ProgressDialog pDialog;
    ArrayList<String> trekking = new ArrayList<String>();
    ParkFacilityAdapter parkFacilityAdapter;
    public static ArrayList<ParkFacilityListItem> ParkCampingFacilityList,ParkTrekkingFacilityList,ParkActivityList ;
    ListView TrekkingData,CampingData;
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String dateTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDatabase = openOrCreateDatabase(Home.DATABASE_NAME, MODE_PRIVATE, null);

        chkCamp = (CheckBox) findViewById(R.id.chkCamp);
        chkTrek = (CheckBox) findViewById(R.id.chkTrek);
        lstActivity = (ListView) findViewById(R.id.lstActivity);
        chkCamp.setOnClickListener(this);
        chkTrek.setOnClickListener(this);

        btnReport = (Button) findViewById(R.id.btnReport);
        //cDate = (TextView) findViewById(R.id.current_date);
        //cWeather = (TextView) findViewById(R.id.weatherCount);

        //Calendar
        txtStart=(EditText)findViewById(R.id.txtStart);
        /*day=mcalendar.get(Calendar.DAY_OF_MONTH);
        year=mcalendar.get(Calendar.YEAR);
        month=mcalendar.get(Calendar.MONTH);*/


        //calendar

        sharedpreferences = getSharedPreferences(Home.MyPREFERENCES, Context.MODE_PRIVATE);
        NPID = sharedpreferences.getInt("NPID", 0);
        parkName = sharedpreferences.getString("ParkName","");
        parkDistance = sharedpreferences.getString("ParkDistance","");
        area = sharedpreferences.getString("ParkArea","");
        latitude = sharedpreferences.getString("ParkLatitude","");
        longitude = sharedpreferences.getString("ParkLongitude","");

        TextView NParkName = (TextView)findViewById(R.id.NParkName);
        TextView ParkDistance = (TextView)findViewById(R.id.ParkDistance);
        TextView Area = (TextView)findViewById(R.id.Area);
        ImageButton btnNavigation = (ImageButton) findViewById(R.id.btnNavigation);
        NParkName.setText(parkName);
        ParkDistance.setText("Distance (KM): " + parkDistance);
        Area.setText("Area (ft2): "+area);
        //Toast.makeText(getApplicationContext()," "+ NPID +"",Toast.LENGTH_LONG).show();




        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ParkLatitude",latitude);
                editor.putString("ParkLongitude", longitude);
                editor.putString("ParkName",parkName);
                editor.putString("ParkArea",area);
                editor.putString("ParkDistance",parkDistance);
                editor.apply();
                startActivity(new Intent(ParkActivity.this,MapsActivity.class));

            }
        });

      //  Calendar cal = Calendar.getInstance();
    //    dateTime = sdf.format(cal.getTime());
//        cDate.setText(dateTime);
  //      cWeather.setText(30+"");
        setTitle(parkName);

        ParkCampingFacilityList = new ArrayList<ParkFacilityListItem>();
        ParkTrekkingFacilityList = new ArrayList<ParkFacilityListItem>();
        ParkActivityList = new ArrayList<ParkFacilityListItem>();

        new GetParksFacilityData().execute();

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReport = new Intent(ParkActivity.this, report.class);
                startActivity(intentReport);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (chkCamp.isChecked()) {
            if (chkTrek.isChecked()) {
                ParkActivityList.clear();
                ParkActivityList = ParkCampingFacilityList;
                ParkActivityList.addAll( ParkTrekkingFacilityList );
                Toast.makeText(getApplicationContext(), ""+ ParkActivityList.size()+ "", Toast.LENGTH_LONG).show();
                onCheckChanged();

            } else {

                ParkActivityList.clear();
                ParkActivityList = ParkCampingFacilityList;
                onCheckChanged();
                //Toast.makeText(getApplicationContext(), "Camp checked", Toast.LENGTH_LONG).show();
            }
        }
        else if (chkTrek.isChecked()) {

            ParkActivityList.clear();
            ParkActivityList = ParkTrekkingFacilityList;
            onCheckChanged();

        }
        else {
            Toast.makeText(getApplicationContext(), "Please select at least one activity", Toast.LENGTH_LONG).show();
        }


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
            ParkActivityList.clear();
        }

        @Override
        protected String doInBackground(String... params) {

            String z = "SUCCESS";
            if (firstTimeLoading) {
                firstTimeLoading = false;
                try {

                    String query1 = "select * from tbl_Camping where NPID =" + NPID + " order by CampingDescription ASC";
                    Cursor cursorCamping = mDatabase.rawQuery(query1, null);

                    if (cursorCamping.moveToFirst()) {
                        do {
                            final ParkFacilityListItem campItem = new ParkFacilityListItem();
                            campItem.setParkFacilityName(cursorCamping.getString(cursorCamping.getColumnIndex("CampingDescription")));
                            campItem.setParkFacilityID(cursorCamping.getInt(cursorCamping.getColumnIndex("CampID")));
                            ParkCampingFacilityList.add(campItem);
                        } while (cursorCamping.moveToNext());
                    }

                    cursorCamping.close();
                } catch (Exception e) {
                    z = "FAIL";
                }
                try {

                    String query = "select * from tbl_Treck_Trail where NPID =" + NPID + " order by TrackName ASC";
                    Cursor cursorTrekking = mDatabase.rawQuery(query, null);

                    if (cursorTrekking.moveToFirst()) {
                        do {
//                        trekking.add(cursorTrekking.getString(cursorTrekking.getColumnIndex("TrackName")));
                            final ParkFacilityListItem trekItem = new ParkFacilityListItem();
                            trekItem.setParkFacilityName(cursorTrekking.getString(cursorTrekking.getColumnIndex("TrackName")));
                            trekItem.setParkFacilityID(cursorTrekking.getInt(cursorTrekking.getColumnIndex("TID")));
                            ParkTrekkingFacilityList.add(trekItem);
                        } while (cursorTrekking.moveToNext());
                    }
                    cursorTrekking.close();
                } catch (Exception e) {
                    z = "FAIL";
                }
            }

            ParkActivityList = ParkCampingFacilityList;
            ParkActivityList.addAll( ParkTrekkingFacilityList );
            return z;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.hide();

            if(result.equals("SUCCESS")){

                if(ParkActivityList.size()>0){
                    lstActivity.setVisibility(View.VISIBLE);
                    ParkFacilityAdapter parkListAdapter = new ParkFacilityAdapter(ParkActivity.this,ParkActivityList);
                    lstActivity.setAdapter(parkListAdapter);
                    lstActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    lstActivity.setAdapter(null);
                    lstActivity.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Camping Data Not Available to this "+parkName,Toast.LENGTH_LONG).show();
                }


            }else{
                Toast.makeText(getApplicationContext(),"Loading Problem",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onCheckChanged(){

        if(ParkActivityList.size()>0){
            lstActivity.setVisibility(View.VISIBLE);
            ParkFacilityAdapter parkListAdapter = new ParkFacilityAdapter(ParkActivity.this,ParkActivityList);
            lstActivity.setAdapter(parkListAdapter);
            lstActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            lstActivity.setAdapter(null);
            lstActivity.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Camping Data Not Available to this "+parkName,Toast.LENGTH_LONG).show();
        }
    }

}
