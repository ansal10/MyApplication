package org.common.api.requests.mapper.trainatstation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amd on 8/26/15.
 */
public class StationTrain {

    private Long number;
    private String name;
    @SerializedName("actarr")
    private String actualArrival;
    @SerializedName("actdep")
    private String actualDeparture;
    @SerializedName("schdep")
    private String scheduleDeparture;
    @SerializedName("scharr")
    private String scheduleArrival;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
