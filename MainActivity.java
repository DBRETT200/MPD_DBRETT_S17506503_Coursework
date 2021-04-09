package org.me.gcu.dbrett_s1706053_coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.me.gcu.dbrett_s1706053_coursework.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

//Daniel Brett
//S1706503

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener
{
    private ListView listView;
    private Button enterDateButton;
    private Button viewMapButton;
    private ToggleButton strengthToggleButton;
    private String dataToParse;
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    LinkedList<EarthquakeFeedItem> aEarthquakeList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        viewMapButton = (Button) findViewById(R.id.viewMapButton);
        viewMapButton.setOnClickListener(this);

        enterDateButton = (Button) findViewById(R.id.enterDateButton);
        enterDateButton.setOnClickListener(this);

        strengthToggleButton = (ToggleButton) findViewById(R.id.strengthToggleButton);
        strengthToggleButton.setOnClickListener(this);

        Log.e("MyTag","after startButton");
        // More Code goes here
        listView = (ListView) findViewById(R.id.earthquakeList);
        listView.setOnItemClickListener(this);
        new GatherEarthquakeData(listView, this).execute();

    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
       EarthquakeDescriptionInformation earthquakeInfo = (EarthquakeDescriptionInformation)parent.getAdapter().getItem(position);
       showEarthquakeInfoDialogScroll(earthquakeInfo);
    }

    public void onClick(View aview)
    {
      if(aview == strengthToggleButton) {
          if (strengthToggleButton.isChecked())
          {

              ArrayList<EarthquakeDescriptionInformation> earthquakeDescriptionDetailsForSort = new ArrayList<>();
              for (int i = 0; i < aEarthquakeList.size(); i++) {
                  EarthquakeDescriptionInformation earthquakeDetailsForSort = aEarthquakeList.get(i).getItemDescription();
                  earthquakeDescriptionDetailsForSort.add(earthquakeDetailsForSort);
              }

              Collections.sort(earthquakeDescriptionDetailsForSort, new Comparator<EarthquakeDescriptionInformation>() {
                  @Override
                  public int compare(EarthquakeDescriptionInformation o1, EarthquakeDescriptionInformation o2) {

                      int compareMagnitude = 0;
                      int earthquakeMagnitudeRounded = 0;

                      compareMagnitude = Math.round(o1.getEarthquakeMagnitude());
                      earthquakeMagnitudeRounded = Math.round(o2.earthquakeMagnitude);

                      return earthquakeMagnitudeRounded - compareMagnitude;
                  }
              });

              EarthquakeListAdapter earthquakeDataAdapterSorted = new EarthquakeListAdapter(this, R.layout.activity_list_item, earthquakeDescriptionDetailsForSort);

              //Assign the adapter to the Earthquake ListView
              listView.setAdapter(earthquakeDataAdapterSorted);
          }
          else
          {
              ArrayList<EarthquakeDescriptionInformation> earthquakeDescriptionDetailsUnsorted = new ArrayList<>();
              for (int i = 0; i < aEarthquakeList.size(); i++) {
                  EarthquakeDescriptionInformation earthquakeDetailsUnsorted = aEarthquakeList.get(i).getItemDescription();
                  earthquakeDescriptionDetailsUnsorted.add(earthquakeDetailsUnsorted);
              }


              EarthquakeListAdapter earthquakeDataAdapterUnsorted = new EarthquakeListAdapter(this, R.layout.activity_list_item, earthquakeDescriptionDetailsUnsorted);

              //Assign the adapter to the Earthquake ListView
              listView.setAdapter(earthquakeDataAdapterUnsorted);
          }
      }
      else if (aview == enterDateButton){
          Intent intent = new Intent(this, DatePickerActivity.class);
          intent.putExtra("earthquakeList", aEarthquakeList);
          startActivity(intent);
      }
      else if(aview == viewMapButton){
          Intent intent = new Intent(this, MapActivity.class);
          intent.putExtra("earthquakeList", aEarthquakeList);
          startActivity(intent);
      }
    }

    private void showEarthquakeInfoDialogScroll(EarthquakeDescriptionInformation info)
    {
        // Custom dialog setup
        Log.e("Mytag","In scroll dialog");
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_info_scroll);
        dialog.setTitle(info.getEarthquakeLocation());
        Log.e("Mytag","In scroll dialog after set up");
        // Set the custom dialog components as a TextView and Button component
        TextView text = (TextView) dialog.findViewById(R.id.infoViewScroll);
        String infoText = "Location: " + info.getEarthquakeLocation() + System.lineSeparator() + "Date & Time: " + info.getEarthquakeOriginDateTime() + System.lineSeparator()
                           + "Latitude/Longitude: " + info.getEarthquakeLatLong() + System.lineSeparator() + "Earthquake Depth: " + info.getEarthquakeDepth() + System.lineSeparator()
                           + "Earthquake Magnitude: " + info.getEarthquakeMagnitude();
        text.setText(infoText);
        Button dialogButton = (Button) dialog.findViewById(R.id.finishOK);
        dialogButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();

            }
        });

        dialog.show();
    }



    private class GatherEarthquakeData extends AsyncTask<String, Integer, String>
    {
        private ListView listView;
        private Context context;

        public GatherEarthquakeData(ListView listView, Context context){
            this.listView = listView;
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            // update the UI immediately after the task is executed
            super.onPreExecute();

            Toast.makeText(MainActivity.this,"Gathering most recent earthquake data", Toast.LENGTH_SHORT).show();


        }

        @Override
        protected String doInBackground(String... params)
        {
                URL aUrl;
                HttpURLConnection URLConnection = null;
                BufferedReader in = null;
                String inputLine = "";


                Log.e("MyTag","in run");

                try
                {
                    Log.e("MyTag","in try");
                    aUrl = new URL(urlSource);
                    URLConnection = (HttpURLConnection) aUrl.openConnection();
                    in = new BufferedReader(new InputStreamReader(URLConnection.getInputStream()));
                    Log.e("MyTag","after ready");
                    //
                    //Throw away the first 2 header lines before parsing
                    for(int i = 0; i < 13;) {
                      in.readLine();
                      i++;
                    }
                    while ((inputLine = in.readLine()) != null)
                    {
                        dataToParse = dataToParse + inputLine;
                        Log.e("MyTag",inputLine);
                    }
                    in.close();
                }
                catch (IOException ae)
                {
                    Log.e("MyTag", "ioexception in run");
                }

                // Really need to do some calculation on progress

            return "<rss><channel>" + dataToParse;
        }


        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            aEarthquakeList = parseData(result);

            ArrayList<EarthquakeDescriptionInformation> earthquakeDescriptionDetails = new ArrayList<>();
            for (int i = 0; i < aEarthquakeList.size(); i++)
            {
                EarthquakeDescriptionInformation earthquakeDetails = aEarthquakeList.get(i).getItemDescription();
                earthquakeDescriptionDetails.add(earthquakeDetails);
            }

            EarthquakeListAdapter earthquakeDataAdapter = new EarthquakeListAdapter(context, R.layout.activity_list_item, earthquakeDescriptionDetails);

            //Assign the adapter to the Earthquake ListView
            listView.setAdapter(earthquakeDataAdapter);

            Toast.makeText(MainActivity.this,
                    "Earthquake Data Gathered", Toast.LENGTH_SHORT).show();


        }

    } // End of AsyncTask

    private LinkedList<EarthquakeFeedItem> parseData(String dataToParse)
    {
        EarthquakeFeedItem earthquake = null;
        EarthquakeDescriptionInformation earthquakeDescriptionInfo = null;
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(dataToParse));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        aEarthquakeList  = new LinkedList<EarthquakeFeedItem>();
                    }
                    else if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        Log.e("MyTag","Item Start Tag found");
                        earthquake = new EarthquakeFeedItem();
                        earthquakeDescriptionInfo = new EarthquakeDescriptionInformation();
                    }
                    else if (xpp.getName().equalsIgnoreCase("title"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText().replaceAll("<[^>]+>", "");
                        // Do something with text
                        Log.e("MyTag","Title is " + temp);
                        earthquake.setItemTitle(temp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("description"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText().replaceAll("<[^>]+>", "");
                        // Do something with text
                        Log.e("MyTag","Description is " + temp);
                        String[] tempResult = temp.split(";");

                        String[] tempOriginTimeDate = tempResult[0].split(":", 2);
                        String currentOriginTimeDate = tempOriginTimeDate[1];

                        Log.e("MyTag","Description is " + currentOriginTimeDate);
                        earthquakeDescriptionInfo.earthquakeOriginDateTime = currentOriginTimeDate;

                        String[] tempLocation = tempResult[1].split(":");
                        String currentLocation = tempLocation[1];
                        Log.e("MyTag","Description is " + currentLocation);
                        earthquakeDescriptionInfo.earthquakeLocation = currentLocation;

                        String[] tempLatLong = tempResult[2].split(":");
                        String currentLatLong = tempLatLong[1];
                        Log.e("MyTag","Description is " + currentLatLong);
                        earthquakeDescriptionInfo.earthquakeLatLong = currentLatLong;

                        String[] tempDepth = tempResult[3].split(":");
                        String currentDepthDirty = tempDepth[1];
                        String currentDepthClean = currentDepthDirty.replaceAll("km", "").trim();
                        Log.e("MyTag","Description is " + currentDepthClean);
                        earthquakeDescriptionInfo.earthquakeDepth = Integer.parseInt(currentDepthClean);

                        String[] tempMagnitude = tempResult[4].split(":");
                        String currentMagnitude = tempMagnitude[1];
                        earthquakeDescriptionInfo.earthquakeMagnitude = Float.parseFloat(currentMagnitude);

                        earthquake.setItemDescription(earthquakeDescriptionInfo);
                    }
                    else if (xpp.getName().equalsIgnoreCase("link"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText().replaceAll("<[^>]+>", "");
                        // Do something with text
                        Log.e("MyTag","Link is " + temp);
                        earthquake.setItemLink(temp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("pubDate"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText().replaceAll("<[^>]+>", "");
                        // Do something with text
                        Log.e("MyTag","Publication Date is " + temp);
                        earthquake.setItemPublicationDate(temp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("category"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText().replaceAll("<[^>]+>", "");
                        // Do something with text
                        Log.e("MyTag","Category is " + temp);
                        earthquake.setItemCategory(temp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("geo:lat"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText().replaceAll("<[^>]+>", "");
                        // Do something with text
                        Log.e("MyTag","Geo:Lat is " + temp);
                        earthquake.setItemGeoLat(Float.parseFloat(temp));
                    }
                    else if (xpp.getName().equalsIgnoreCase("geo:long"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText().replaceAll("<[^>]+>", "");
                        // Do something with text
                        Log.e("MyTag","Geo:Long is " + temp);
                        earthquake.setItemGeoLong(Float.parseFloat(temp));
                    }
                }
                else if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        Log.e("MyTag","widget is " + earthquake.toString());
                        aEarthquakeList.add(earthquake);
                    }
                    else if (xpp.getName().equalsIgnoreCase("/channel"))
                    {
                        int size;
                        size = aEarthquakeList.size();
                        Log.e("MyTag","Earthquake Data size is " + size);
                    }
                }


                // Get the next event
                eventType = xpp.next();

            } // End of while

        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag","End document");

        return aEarthquakeList;
    }

}