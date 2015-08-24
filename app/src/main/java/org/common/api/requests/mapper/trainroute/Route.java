package org.common.api.requests.mapper.trainroute;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amd on 8/21/15.
 */
public class Route {

    private Long no;
    private Double distance;
    private Long day;
    private Long halt;
    private Long route;
    @SerializedName("code")
    private String stationCode;
    @SerializedName("fullname")
    private String stationName;
    private Double lat;
    private Double lng;
    private String state;
    @SerializedName("scharr")
    private String scheduleArrival;
    @SerializedName("schdep")
    private String scheduleDeparture;

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getHalt() {
        return halt;
    }

    public void setHalt(Long halt) {
        this.halt = halt;
    }

    public Long getRoute() {
        return route;
    }

    public void setRoute(Long route) {
        this.route = route;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getScheduleArrival() {
        return scheduleArrival;
    }

    public void setScheduleArrival(String scheduleArrival) {
        this.scheduleArrival = scheduleArrival;
    }

    public String getScheduleDeparture() {
        return scheduleDeparture;
    }

    public void setScheduleDeparture(String scheduleDeparture) {
        this.scheduleDeparture = scheduleDeparture;
    }
}
