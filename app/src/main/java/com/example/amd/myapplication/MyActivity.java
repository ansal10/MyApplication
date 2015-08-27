package com.example.amd.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;

public class MyActivity extends Activity {

    public final String STATION_CODES_FILES = "TrainStationCodes.csv";

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {

    }

    public void seatAvailiblityClick(View view) {
    }

    public void trainRoutesClick(View view) {


    }

    public void trainBetweenStationsClick(View view) throws IOException {
        Intent intent = new Intent(this, TrainBetweenStations.class);
        startActivity(intent);
    }

    public void trainRunningStatus(View view) {
        Intent intent = new Intent(this, TrainRunningStatusActivity.class);
        startActivity(intent);
    }
}
