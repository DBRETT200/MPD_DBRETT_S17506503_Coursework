package org.me.gcu.dbrett_s1706053_coursework;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//Daniel Brett
//S1706503

public class EarthquakeListAdapter extends ArrayAdapter<EarthquakeDescriptionInformation> {

    ArrayList<EarthquakeDescriptionInformation> earthquakeDetailsList = new ArrayList<EarthquakeDescriptionInformation>();


    public EarthquakeListAdapter(Context context, int textViewResourceId, ArrayList<EarthquakeDescriptionInformation> objects) {
        super(context, textViewResourceId, objects);
        earthquakeDetailsList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_list_item, null);
        TextView earthquakeLocationTextView = (TextView) v.findViewById(R.id.textView_earthquakeLocation);
        TextView earthquakeMagnitudeTextView = (TextView) v.findViewById(R.id.textView_earthquakeMagnitude);
        earthquakeLocationTextView.setText("Location:" + earthquakeDetailsList.get(position).getEarthquakeLocation());
        earthquakeMagnitudeTextView.setText("Magnitude: " + String.valueOf(earthquakeDetailsList.get(position).getEarthquakeMagnitude()));

        if(earthquakeDetailsList.get(position).getEarthquakeMagnitude() > 2) {
            v.setBackgroundColor(Color.RED);
        }
        else if (earthquakeDetailsList.get(position).getEarthquakeMagnitude() > 1.2){
            v.setBackgroundColor(Color.YELLOW);
        }
        else {
            v.setBackgroundColor(Color.GREEN);
        }



        return v;

    }

}
