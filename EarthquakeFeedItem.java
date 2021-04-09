package org.me.gcu.dbrett_s1706053_coursework;

import java.io.Serializable;

//Daniel Brett
//S1706503

public class EarthquakeFeedItem implements Serializable {

    String itemTitle = "";
    EarthquakeDescriptionInformation itemDescription = new EarthquakeDescriptionInformation();
    String itemLink = "";
    String itemPublicationDate = "";
    String itemCategory = "";
    float itemGeoLat = 0.00f;
    float itemGeoLong = 0.00f;

    //Constructors for the EarthquakeFeedItem Class
    public EarthquakeFeedItem() {}

    public EarthquakeFeedItem(String itemTitle, EarthquakeDescriptionInformation itemDescription, String itemLink, String itemPublicationDate, String itemCategory, float itemGeoLat, float itemGeoLong)
    {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemLink = itemLink;
        this.itemPublicationDate = itemPublicationDate;
        this.itemCategory = itemCategory;
        this.itemGeoLat = itemGeoLat;
        this.itemGeoLong = itemGeoLong;
    }

    //Below are the GETTERS for the information stored within an Item from the earthquake feed

    public String getItemTitle() {
        return itemTitle;
    }

    public EarthquakeDescriptionInformation getItemDescription() {
        return itemDescription;
    }

    public String getItemLink() {
        return itemLink;
    }

    public String getItemPublicationDate() {
        return itemPublicationDate;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public float getItemGeoLat() {
        return itemGeoLat;
    }

    public float getItemGeoLong() {
        return itemGeoLong;
    }

    //Below are the SETTERS for the information stored within an Item from the earthquake feed

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public void setItemDescription(EarthquakeDescriptionInformation itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public void setItemPublicationDate(String itemPublicationDate) { this.itemPublicationDate = itemPublicationDate; }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemGeoLat(float itemGeoLat) {
        this.itemGeoLat = itemGeoLat;
    }

    public void setItemGeoLong(float itemGeoLong) {
        this.itemGeoLong = itemGeoLong;
    }
}
