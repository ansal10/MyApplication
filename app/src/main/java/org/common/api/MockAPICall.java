package org.common.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.common.api.requests.mapper.seatavailiblity.Availiblity;
import org.common.api.requests.mapper.seatavailiblity.SeatAvailiblity;
import org.common.api.requests.mapper.trainbetweenstations.Train;
import org.common.api.requests.mapper.trainbetweenstations.TrainBetweenStations;
import org.common.api.requests.mapper.trainroute.TrainRoute;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amd on 8/21/15.
 */
public class MockAPICall {


    private static final String SUCCESS_RESPONSE="200";

    private static final String TRAIN="[[TRAIN]]";
    private static final String SOURCE="[[SOURCE]]";
    private static final String DEST="[[DESTINATION]]";
    private static final String DATE="[[DATE]]";
    private static final String CLASS="[[CLASS]]";
    private static final String QUOTA="[[QUOTA]]";
    private static final String APIKEY="[[APIKEY]]";


    private static final String API_KEY="xpxgh9950";


    private static final String SEAT_AVAILIBLITY = "http://api.railwayapi.com/check_seat/train/[[TRAIN]]/source/[[SOURCE]]/dest/[[DESTINATION]]/date/[[DATE]]/class/[[CLASS]]/quota/[[QUOTA]]/apikey/[[APIKEY]]/";
    private static final String TRAIN_BETWEEN_STATION = "http://api.railwayapi.com/between/source/[[SOURCE]]/dest/[[DESTINATION]]/date/[[DATE]]/apikey/[[APIKEY]]/";
    private static final String TRAIN_ROUTE = "http://api.railwayapi.com/route/train/[[TRAIN]]/apikey/[[APIKEY]]/";



    public static int calls=0;

    public static String HTTPCall(String urlString){

        if(!canCall())
            return "{ responseCode : 403 }";

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            String response = IOUtils.toString(is);
            System.out.println(url);
            System.out.println(response);
            calls++;
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return "{ responseCode : 403 }";
        }
    }

    public static boolean canCall(){
        return calls <= 10000;
    }


    public static List<Availiblity> getAvailiblity(String train, String source, String destination, String date, String cclass, String quota){

        String url = SEAT_AVAILIBLITY
                .replace(TRAIN, train)
                .replace(SOURCE, source)
                .replace(DEST, destination)
                .replace(DATE, date)
                .replace(CLASS, cclass)
                .replace(QUOTA, quota)
                .replace(APIKEY, API_KEY);


        String response = HTTPCall(url);
        int times=5;

        while(times>=0 && response.contains("\"error\": true")){
            response = HTTPCall(url);
            times--;
        }
        System.out.println("Tried "+(5-times)+" times");
        Gson gson =new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        SeatAvailiblity seatAvailiblity = gson.fromJson( response , SeatAvailiblity.class);
        if(seatAvailiblity.getResponseCode().equals(SUCCESS_RESPONSE)){
            return seatAvailiblity.getAvailability();
        }else{
            return new ArrayList<>();
        }
    }

    public static List<Train> getTrainsBetweenStation(String source , String dest, String date){
        String response = "{ \"train\": [ { \"src_departure_time\": \"00:05\", \"dest_arrival_time\": \"06:40\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12553\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 1, \"name\": \"VAISHALI EXP\" }, { \"src_departure_time\": \"00:13\", \"dest_arrival_time\": \"07:35\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"N\", \"day-code\": \"THU\" }, { \"runs\": \"N\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12303\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 2, \"name\": \"POORVA EXPRESS\" }, { \"src_departure_time\": \"00:15\", \"dest_arrival_time\": \"07:05\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12417\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 3, \"name\": \"PRAYAG RAJ EXP\" }, { \"src_departure_time\": \"00:50\", \"dest_arrival_time\": \"07:05\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12225\", \"to\": { \"code\": \"DLI\", \"name\": \"DELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 4, \"name\": \"KAIFIYAT EXP\" }, { \"src_departure_time\": \"01:38\", \"dest_arrival_time\": \"08:10\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12559\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 5, \"name\": \"SHIV GANGA EXP\" }, { \"src_departure_time\": \"02:35\", \"dest_arrival_time\": \"07:40\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12309\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 6, \"name\": \"RJPB RAJDHANI\" }, { \"src_departure_time\": \"03:00\", \"dest_arrival_time\": \"08:20\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"N\", \"day-code\": \"WED\" }, { \"runs\": \"N\", \"day-code\": \"THU\" }, { \"runs\": \"N\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"N\", \"day-code\": \"SUN\" } ], \"number\": \"12569\", \"to\": { \"code\": \"ANVT\", \"name\": \"ANANDVIHARTRM\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 7, \"name\": \"JYG ANVT G RATH\" }, { \"src_departure_time\": \"03:25\", \"dest_arrival_time\": \"11:50\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12401\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 8, \"name\": \"MAGADH EXPRESS\" }, { \"src_departure_time\": \"04:00\", \"dest_arrival_time\": \"11:50\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"N\", \"day-code\": \"WED\" }, { \"runs\": \"N\", \"day-code\": \"THU\" }, { \"runs\": \"N\", \"day-code\": \"FRI\" }, { \"runs\": \"N\", \"day-code\": \"SAT\" }, { \"runs\": \"N\", \"day-code\": \"SUN\" } ], \"number\": \"22857\", \"to\": { \"code\": \"ANVT\", \"name\": \"ANANDVIHARTRM\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 9, \"name\": \"SRC ANVT SF EXP\" }, { \"src_departure_time\": \"04:20\", \"dest_arrival_time\": \"12:15\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12581\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 10, \"name\": \"MUV NDLS S F EX\" }, { \"src_departure_time\": \"04:53\", \"dest_arrival_time\": \"10:00\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12301\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 11, \"name\": \"KOLKATA RAJDHNI\" }, { \"src_departure_time\": \"05:08\", \"dest_arrival_time\": \"10:15\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12423\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 12, \"name\": \"DBRT RAJDHANI E\" }, { \"src_departure_time\": \"05:18\", \"dest_arrival_time\": \"10:25\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12313\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 13, \"name\": \"SDAH RAJDHANIEX\" }, { \"src_departure_time\": \"05:28\", \"dest_arrival_time\": \"10:40\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"N\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"N\", \"day-code\": \"SUN\" } ], \"number\": \"22823\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 14, \"name\": \"BBS RAJDHANI EX\" }, { \"src_departure_time\": \"05:50\", \"dest_arrival_time\": \"10:50\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"N\", \"day-code\": \"THU\" }, { \"runs\": \"N\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"N\", \"day-code\": \"SUN\" } ], \"number\": \"12877\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 15, \"name\": \"RNC GARIB RATH\" }, { \"src_departure_time\": \"06:00\", \"dest_arrival_time\": \"11:20\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"N\", \"day-code\": \"SUN\" } ], \"number\": \"12033\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 16, \"name\": \"CNB NDLS SHT\" }, { \"src_departure_time\": \"06:15\", \"dest_arrival_time\": \"12:10\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12561\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 17, \"name\": \"SWATANTRA S EXP\" }, { \"src_departure_time\": \"06:50\", \"dest_arrival_time\": \"13:00\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"N\", \"day-code\": \"WED\" }, { \"runs\": \"N\", \"day-code\": \"THU\" }, { \"runs\": \"N\", \"day-code\": \"FRI\" }, { \"runs\": \"N\", \"day-code\": \"SAT\" }, { \"runs\": \"N\", \"day-code\": \"SUN\" } ], \"number\": \"12349\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 18, \"name\": \"BGP NDLS EXP\" }, { \"src_departure_time\": \"07:25\", \"dest_arrival_time\": \"13:55\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"N\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"N\", \"day-code\": \"SAT\" }, { \"runs\": \"N\", \"day-code\": \"SUN\" } ], \"number\": \"12873\", \"to\": { \"code\": \"ANVT\", \"name\": \"ANANDVIHARTRM\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 19, \"name\": \"JHARKHAND S J E\" }, { \"src_departure_time\": \"07:40\", \"dest_arrival_time\": \"14:55\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12419\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 20, \"name\": \"GOMTI EXP\" }, { \"src_departure_time\": \"08:40\", \"dest_arrival_time\": \"19:40\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"13007\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 21, \"name\": \"U ABHATOOFAN EX\" }, { \"src_departure_time\": \"10:20\", \"dest_arrival_time\": \"17:05\", \"days\": [ { \"runs\": \"N\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"N\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"N\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12815\", \"to\": { \"code\": \"NDLS\", \"name\": \"NEWDELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 22, \"name\": \"NANDAN KANAN EX\" }, { \"src_departure_time\": \"12:25\", \"dest_arrival_time\": \"22:40\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"15483\", \"to\": { \"code\": \"DLI\", \"name\": \"DELHI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 23, \"name\": \"MAHANANDA EXP\" }, { \"src_departure_time\": \"12:35\", \"dest_arrival_time\": \"22:13\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"18101\", \"to\": { \"code\": \"SZM\", \"name\": \"SUBZIMANDI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 24, \"name\": \"TATA JAT EXP\" }, { \"src_departure_time\": \"12:45\", \"dest_arrival_time\": \"21:39\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12311\", \"to\": { \"code\": \"SZM\", \"name\": \"SUBZIMANDI\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 25, \"name\": \"HWH DLI KLK MAI\" }, { \"src_departure_time\": \"12:55\", \"dest_arrival_time\": \"19:20\", \"days\": [ { \"runs\": \"Y\", \"day-code\": \"MON\" }, { \"runs\": \"Y\", \"day-code\": \"TUE\" }, { \"runs\": \"Y\", \"day-code\": \"WED\" }, { \"runs\": \"Y\", \"day-code\": \"THU\" }, { \"runs\": \"Y\", \"day-code\": \"FRI\" }, { \"runs\": \"Y\", \"day-code\": \"SAT\" }, { \"runs\": \"Y\", \"day-code\": \"SUN\" } ], \"number\": \"12505\", \"to\": { \"code\": \"ANVT\", \"name\": \"ANANDVIHARTRM\" }, \"from\": { \"code\": \"CNB\", \"name\": \"KANPUR CENTRAL\" }, \"no\": 26, \"name\": \"NORTH EAST EXP\" } ], \"total\": 26, \"response_code\": 200 }\n";
        Gson gson =new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        TrainBetweenStations trainBetweenStations = gson.fromJson( response, TrainBetweenStations.class);
        if(trainBetweenStations.getResponseCode().equals(SUCCESS_RESPONSE))
            return trainBetweenStations.getTrain();
        else
            return new ArrayList<>();
    }

    public static TrainRoute getTrainRoute(String train){
        String url = TRAIN_ROUTE
                .replace(TRAIN, train)
                .replace(APIKEY, API_KEY);

        String response = HTTPCall(url);
        Gson gson =new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        TrainRoute trainRoute = gson.fromJson(response, TrainRoute.class);

        if(trainRoute.getResponseCode().equals(SUCCESS_RESPONSE))
            return trainRoute;
        else
            return null;


    }
}
