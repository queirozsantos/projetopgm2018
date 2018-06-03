package br.com.queirozted.projetopgm.basicas;

import java.io.Serializable;



public class Geometry implements Serializable {
    private float location_lat;
    private float location_lng;

    public float getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(float location_lat) {
        this.location_lat = location_lat;
    }

    public float getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(float location_lng) {
        this.location_lng = location_lng;
    }

}
