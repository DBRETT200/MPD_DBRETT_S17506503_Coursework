package org.me.gcu.dbrett_s1706053_coursework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Daniel Brett
//S1706503

public class EarthquakeDescriptionInformation implements Serializable {

    String earthquakeOriginDateTime = "";
    String earthquakeLocation = "";
    String earthquakeLatLong = "";
    int earthquakeDepth = 0;
    float earthquakeMagnitude = 0.00f;

    public EarthquakeDescriptionInformation(){ }

    public EarthquakeDescriptionInformation(String earthquakeOriginDateTime, String earthquakeLocation, String earthquakeLatLong, int earthquakeDepth, float earthquakeMagnitude){
        this.earthquakeOriginDateTime = earthquakeOriginDateTime;
        this.earthquakeLocation = earthquakeLocation;
        this.earthquakeLatLong = earthquakeLatLong;
        this.earthquakeDepth = earthquakeDepth;
        this.earthquakeMagnitude = earthquakeMagnitude;
    }


    //Below are the SETTERS for the information stored within the description of an earthquake

    public void setEarthquakeOriginDateTime(String earthquakeOriginDateTime) {
        this.earthquakeOriginDateTime = earthquakeOriginDateTime;
    }

    public void setEarthquakeLocation(String earthquakeLocation) {
        this.earthquakeLocation = earthquakeLocation;
    }

    public void setEarthquakeLatLong(String earthquakeLatLong) {
        this.earthquakeLatLong = earthquakeLatLong;
    }

    public void setEarthquakeDepth(int earthquakeDepth) {
        this.earthquakeDepth = earthquakeDepth;
    }

    public void setEarthquakeMagnitude(float earthquakeMagnitude) {
        this.earthquakeMagnitude = earthquakeMagnitude;
    }


    //GETTERS for the information stored within the description of an earthquake
    public String getEarthquakeOriginDateTime() {
        return earthquakeOriginDateTime;
    }

    public String getEarthquakeLocation() {
        return earthquakeLocation;
    }

    public String getEarthquakeLatLong() {
        return earthquakeLatLong;
    }

    public int getEarthquakeDepth() {
        return earthquakeDepth;
    }

    public float getEarthquakeMagnitude() {
        return earthquakeMagnitude;
    }

}
