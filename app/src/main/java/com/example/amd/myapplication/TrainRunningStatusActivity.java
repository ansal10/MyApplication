package com.example.amd.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.common.api.APICall;
import org.common.api.requests.mapper.livetrain.TrainRunningRoute;
import org.common.api.requests.mapper.livetrain.TrainRunningStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainRunningStatusActivity extends Activity implements View.OnClickListener {

    private Map<String, String> trainNameAndCodeMapping = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_running_status);

        initializeTrainCodeAndName();

        View getRunningStatus = findViewById(R.id.getTrainRunningStatus);
        getRunningStatus.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_train_running_status, menu);
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
        switch (v.getId()) {
            case R.id.getTrainRunningStatus : {

                View viewById = findViewById(R.id.trainRunningStatusHiddenLayout);
                RelativeLayout hiddenLayout = (RelativeLayout) viewById;
                if(hiddenLayout != null){
                    ((ViewGroup) viewById.getParent()).removeView(viewById);
                }

                String trainNumberKey = ((TextView) findViewById(R.id.trainNumberOrPNR)).getText().toString();
                final String trainNumber = trainNameAndCodeMapping.get(trainNumberKey);
                String dayCode = ((TextView)findViewById(R.id.dayCode)).getText().toString();
                String monthCode = ((TextView)findViewById(R.id.monthCode)).getText().toString();
                String yearCode = "20" + ((TextView)findViewById(R.id.yearCode)).getText().toString();
                final String dateOfJourney = yearCode+monthCode+dayCode;



                new AsyncTask<Void, Void, TrainRunningStatus>() {
                    @Override
                    protected TrainRunningStatus doInBackground(Void... params) {
                        return APICall.getLiveTrainStatus(trainNumber, dateOfJourney);
                    }

                    @Override
                    protected void onPostExecute(TrainRunningStatus trainRunningStatus){
                        View trainRunningStatusDetails = findViewById(R.id.trainRunningStatusDetails);
                        View runningTrainDetailView = findViewById(R.id.runningTrainDetailView);
                        trainRunningStatusDetails.setVisibility(View.VISIBLE);
                        if (trainRunningStatus != null && trainRunningStatus.getTotal()>0){
                            TrainRunningRoute source = trainRunningStatus.getRoute().get(0);
                            TrainRunningRoute dest = trainRunningStatus.getRoute().get(trainRunningStatus.getRoute().size()-1);
                            String trainDetailView = trainRunningStatus.getTrainNumber().toString()+
                                    "  "+ source.getStation() +" "+ source.getScheduleDeparture()+
                                    "  "+dest.getStation() + " "+dest.getScheduleArrival();
                            ((TextView)runningTrainDetailView).setText(trainRunningStatus.getPosition());

                            for(TrainRunningRoute trainRoute : trainRunningStatus.getRoute()){
                                LinearLayout myLayout = (LinearLayout) findViewById(R.id.trainRunningLinearLayout);
                                View hiddenInfo = getLayoutInflater().inflate(R.layout.hidden_train_running_status, myLayout, false);
                                View view = hiddenInfo.findViewById(R.id.trainRunningStatusHiddenLayout);
                                view.setId(trainRoute.getNumber().intValue());
                                ((TextView) view.findViewById(R.id.trainRunningStatusStation)).setText(trainRoute.getStation());
                                ((TextView) view.findViewById(R.id.trainRunningStatusSchduleArr)).setText("Schedule Arrival : "+trainRoute.getScheduleArrival());
                                ((TextView) view.findViewById(R.id.trainRunningStatusSchduleDept)).setText("Schedule Departure : "+trainRoute.getScheduleDeparture());
                                ((TextView) view.findViewById(R.id.trainRunningStatusActualArr)).setText("Actual Arrival : "+trainRoute.getActualArrival());
                                ((TextView) view.findViewById(R.id.trainRunningStatusActualDept)).setText("Actual Departure : "+trainRoute.getActualDeparture());
                                myLayout.addView(hiddenInfo);

                            }

                        }
                        else {
                            ((TextView)runningTrainDetailView).setText("Train not runs on this day");

                        }
                    }
                }.execute();



            }
        }
    }


    private void initializeTrainCodeAndName() {
        trainNameAndCodeMapping = new HashMap<>();
        new AsyncTask<TrainRunningStatusActivity, Void , TrainRunningStatusActivity>(){

            @Override
            protected TrainRunningStatusActivity doInBackground(TrainRunningStatusActivity... params) {
                Scanner scanner = null;
                InputStream is = null;
                String line="initial";
                String []reader;
                System.out.print("STARTS AT : " + new Date().getTime());
                try {
                    is = getResources().getAssets().open("traincodeandnames.txt");
                    scanner = new Scanner(is);
                    while (scanner.hasNext()){
                        line  = scanner.nextLine();
                        reader = line.split(",");
                        String key1 = reader[0]+" "+reader[1];
                        trainNameAndCodeMapping.put(key1 , reader[0]);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    System.out.println("ISSue with : "+line);
                }
                finally {
                    if (scanner!=null)
                        scanner.close();
                    if(is!=null)
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    System.out.print("ENDS AT : " + new Date().getTime());
                }
                return params[0];
            }

            @Override
            public void onPostExecute(TrainRunningStatusActivity t){
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(t, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>(trainNameAndCodeMapping.keySet()));
                AutoCompleteTextView trainOrPNRView = (AutoCompleteTextView) findViewById(R.id.trainNumberOrPNR);
                trainOrPNRView.setThreshold(1);
                trainOrPNRView.setAdapter(arrayAdapter);
            }
        }.execute(this);
    }


}
