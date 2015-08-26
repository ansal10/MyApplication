package org.common.api.requests.mapper.seatavailiblity;

/**
 * Created by amd on 8/21/15.
 */
public class Station {

    public Double lat;
    public String name;
    public Double lng;
    public String code;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
