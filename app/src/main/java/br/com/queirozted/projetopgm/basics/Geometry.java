package br.com.queirozted.projetopgm.basics;

import java.io.Serializable;

public class Geometry implements Serializable {
    private Long location_lat;
    private Long location_lng;

    public Long getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(Long location_lat) {
        this.location_lat = location_lat;
    }

    public Long getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(Long location_lng) {
        this.location_lng = location_lng;
    }
}
