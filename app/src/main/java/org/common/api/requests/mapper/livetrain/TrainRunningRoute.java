package org.common.api.requests.mapper.livetrain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amd on 8/26/15.
 */
public class TrainRunningRoute {

    @SerializedName("no")
    private Long number;
    @SerializedName("actarr")
    private String actualArrival;
    @SerializedName("actdep")
    private String actualDeparture;
    @SerializedName("schdep")
    private String scheduleDeparture;
    @SerializedName("scharr")
    private String scheduleArrival;
    private String status;
    private String station;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(String actualArrival) {
        this.actualArrival = actualArrival;
    }

    public String getActualDeparture() {
        return actualDeparture;
    }

    public void setActualDeparture(String actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public String getScheduleDeparture() {
        return scheduleDeparture;
    }

    public void setScheduleDeparture(String scheduleDeparture) {
        this.scheduleDeparture = scheduleDeparture;
    }

    public String getScheduleArrival() {
        return scheduleArrival;
    }

    public void setScheduleArrival(String scheduleArrival) {
        this.scheduleArrival = scheduleArrival;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
