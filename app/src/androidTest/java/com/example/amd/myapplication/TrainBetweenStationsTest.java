package com.example.amd.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by amd on 8/26/15.
 */
public class TrainBetweenStationsTest extends TestCase {


    @Test
    public void testDateFormatter(){
        TrainBetweenStations trainBetweenStations = new TrainBetweenStations();

        String date1 = "10-10-2015";
        String date2 = "10-13-2015";
        String date3 = "29-02-2016";
        String date4 = "29-02-2015";

        assertTrue(trainBetweenStations.isValidDate(date1));
        assertFalse(trainBetweenStations.isValidDate(date2));
        assertTrue(trainBetweenStations.isValidDate(date3));
        assertFalse(trainBetweenStations.isValidDate(date4));
    }

    @Test
    public void testStationCodeAndNameMapping(){

        System.out.println("Starts at "+new Date().getTime());
        TrainBetweenStations trainBetweenStations = new TrainBetweenStations();
        trainBetweenStations.initializeStationCodeAndMapping();
        System.out.println("Ends at " + new Date().getTime());
    }

}