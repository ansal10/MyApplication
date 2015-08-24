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

                LinearLayout hiddedLayout = (LinearLayout)findViewById(R.id.hiddenLayout);
                if(hiddedLayout==null){

                    String sourceStationCode = ((TextView) findViewById(R.id.sourceStationCode)).getText().toString();
                    String destinatonStationCode = ((TextView) findViewById(R.id.destinatonStationCode)).getText().toString();
                    String date = ((TextView) findViewById(R.id.dayCode)).getText().toString();

                    if(NotNullNotEmpty(sourceStationCode, destinatonStationCode, date)) {

                        List<Train> trains = MockAPICall.getTrainsBetweenStation(sourceStationCode, destinatonStationCode, date);
                        if(trains!=null )

                            for(Train train : trains) {

                                String trainNameNumber = train.getName()+ " / "+train.getNumber();
                                String classes =" SL AC 3A";
                                String daysviews="";

//                                for(Classes clas : train.getClasses()){
//                                    classes = classes+clas.getClassCode()+" ";
//                                }
                                for(Day day : train.getDays()){
                                    if(day.getRuns().equals("Y"))
                                        daysviews = daysviews+day.getDayCode().charAt(0)+" ";
                                    else
                                        daysviews = daysviews+"X ";
                                }

                                LinearLayout myLayout = (LinearLayout) findViewById(R.id.linearLayout2);
                                View hiddenInfo = getLayoutInflater().inflate(R.layout.train_between_stations, myLayout, false);
                                View view = hiddenInfo.findViewById(R.id.trainBetweenStationHiddenLayout);
                                ((TextView) view.findViewById(R.id.trainNameNumber)).setText( trainNameNumber );
                                ((TextView) view.findViewById(R.id.classView)).setText( classes );
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
