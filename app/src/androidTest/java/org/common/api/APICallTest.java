package org.common.api;

import static org.junit.Assert.*;

/**
 * Created by amd on 8/23/15.
 */
import junit.framework.TestCase;
import org.common.api.requests.mapper.seatavailiblity.Availiblity;
import org.common.api.requests.mapper.trainbetweenstations.Train;
import org.common.api.requests.mapper.trainroute.TrainRoute;
import org.junit.Test;

import java.util.List;

public class APICallTest extends TestCase {

    @Test
    public void testNull(){
        assertTrue(true);
    }
//
//    APICall apiCall = new APICall();
//
//    @Test
//    public void testSeatAvailiblity(){
//        String src="BPL";
//        String dest="NDLS";
//        String date="22-10-2015";
//        String classs="CC";
//        String quota="GN";
//        String train="12001";
//
//        List<Availiblity> availiblities = apiCall.getAvailiblity(train,src,dest,date,classs,quota);
//
//        assertTrue((availiblities != null && availiblities.size() > 0));
//
//    }
//
//    @Test
//    public void testTrainBetweenStation(){
//        String src="NDLS";
//        String dest="CNB";
//        String date="22-10-2015";
//
//        List<Train> trains = apiCall.getTrainsBetweenStation(src, dest, date);
//
//        assertTrue( trains!=null && trains.size()>0 );
////        System.out.println(trains);
//    }
//
//    @Test
//    public void testTrainRoute(){
//        String train="12511";
//
//        TrainRoute trainRoute = apiCall.getTrainRoute(train);
//
//        assertEquals(trainRoute.getRoute().size(), 61);
//    }

}