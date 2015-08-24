package com.example.amd.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.common.api.APICall;
import org.common.api.MockAPICall;
import org.common.api.requests.mapper.trainbetweenstations.Classes;
import org.common.api.requests.mapper.trainbetweenstations.Day;
import org.common.api.requests.mapper.trainbetweenstations.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainBetweenStations extends Activity implements View.OnClickListener{

    public static int numOfViews = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_stations);

        View buttonSearch = findViewById(R.id.searchTrainBetweenStaions);
        buttonSearch.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_train_between_stations, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchTrainBetweenStaions:

                View viewById = findViewById(R.id.trainBetweenStationHiddenLayout);
                RelativeLayout hiddedLayout = (RelativeLayout) viewById;
                {
                    if(hiddedLayout!=null)
                        ((ViewGroup)hiddedLayout.getParent()).removeView(viewById);

                    final String sourceStationCode = ((TextView) findViewById(R.id.sourceStationCode)).getText().toString();
                    final String destinatonStationCode = ((TextView) findViewById(R.id.destinatonStationCode)).getText().toString();
                    final String date = ((TextView) findViewById(R.id.dayCode)).getText().toString();

                    if(NotNullNotEmpty(sourceStationCode, destinatonStationCode, date)) {

                        final List<Train> trains = new ArrayList<>();

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                trains.addAll(APICall.getTrainsBetweenStation(sourceStationCode, destinatonStationCode, date));
                            }
                        });
                        t.start();
                        try {
                            t.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(trains!=null )

                            for(Train train : trains) {

                                String trainNameNumber = train.getName()+ " / "+train.getNumber();
                                String arrivalDeparture ="";
                                String daysviews="";

                                for(Day day : train.getDays()){
                                    if(day.getRuns().equals("Y"))
                                        daysviews = daysviews+day.getDayCode().charAt(0)+" ";
                                    else
                                        daysviews = daysviews+"x ";
                                }
                                arrivalDeparture = "Src "+train.getSrcDepartureTime()+" Dest "+train.getDestArrivalTime();

                                LinearLayout myLayout = (LinearLayout) findViewById(R.id.linearLayout2);
                                View hiddenInfo = getLayoutInflater().inflate(R.layout.train_between_stations, myLayout, false);
                                View view = hiddenInfo.findViewById(R.id.trainBetweenStationHiddenLayout);
                                ((TextView) view.findViewById(R.id.trainNameNumber)).setText( trainNameNumber );
                                ((TextView) view.findViewById(R.id.arrivalDepartureView)).setText( arrivalDeparture );
                                ((TextView) view.findViewById(R.id.daysView)).setText( daysviews );
                                myLayout.addView(hiddenInfo);
                                numOfViews++;

                            }
                    }
                }
                //TextView myTextView = (TextView) findViewById(R.id.textView1);
                //myTextView.setText("This is not the original Text defined in the XML layout !");
                break;
        }
    }

    private boolean NotNullNotEmpty(String... params) {
        for(String p:params){
            if(p==null || p.trim().equals(""))
                return false;
        }
        return true;
    }
}
