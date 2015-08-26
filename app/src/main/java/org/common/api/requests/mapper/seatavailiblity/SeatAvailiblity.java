package org.common.api.requests.mapper.seatavailiblity;

import java.util.List;

/**
 * Created by amd on 8/21/15.
 */
public class SeatAvailiblity {

    private String responseCode;
    public Long trainNumber;
    public String trainName;
    public Boolean error;
    public Station from;
    public Station to;
    public Classs classs;
    public List<Availiblity> availability;
    public Quota quota;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Long getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Station getFrom() {
        return from;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public Station getTo() {
        return to;
    }

    public void setTo(Station to) {
        this.to = to;
    }

    public Classs getClasss() {
        return classs;
    }

    public void setClasss(Classs classs) {
        this.classs = classs;
    }

    public List<Availiblity> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availiblity> availability) {
        this.availability = availability;
    }

    public Quota getQuota() {
        return quota;
    }

    public void setQuota(Quota quota) {
        this.quota = quota;
    }
}
