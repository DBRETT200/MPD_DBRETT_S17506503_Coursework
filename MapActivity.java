package org.me.gcu.dbrett_s1706053_coursework;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.LinkedList;

//Daniel Brett
//S1706503
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    private ViewSwitcher mapViewSwitcher;
    private Button s1Button;
    private Button s2Button;
    private Button backButton;
    private RadioGroup mapTypeGroup;
    private RadioButton normalViewButton;
    private RadioButton terrainViewButton;
    private RadioButton hybridViewButton;
    private RadioButton satelliteViewButton;
    private CheckBox panZoom;
    ArrayList<EarthquakeFeedItem> aEarthquakeData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapTypeGroup = (RadioGroup)findViewById(R.id.mapTypeGroup);
        normalViewButton = (RadioButton)findViewById(R.id.normalViewRadio);
        terrainViewButton = (RadioButton)findViewById(R.id.terrainViewRadio);
        hybridViewButton = (RadioButton)findViewById(R.id.hybridViewRadio);
        satelliteViewButton = (RadioButton)findViewById(R.id.satelliteViewRadio);
        panZoom = (CheckBox)findViewById(R.id.panZoom);

        backButton = (Button)findViewById(R.id.backButton);

        Log.e(getPackageName(), "just before avw");
        mapViewSwitcher = (ViewSwitcher) findViewById(R.id.vwSwitch);
        if (mapViewSwitcher == null)
        {
            Toast.makeText(getApplicationContext(), "Null ViewSwicther", Toast.LENGTH_LONG);
            Log.e(getPackageName(), "null pointer");
        }
        s1Button = (Button) findViewById(R.id.screen1Button);
        s2Button = (Button) findViewById(R.id.screen2Button);
        s1Button.setOnClickListener(this);
        s2Button.setOnClickListener(this);
        normalViewButton.setOnClickListener(this);
        terrainViewButton.setOnClickListener(this);
        hybridViewButton.setOnClickListener(this);
        satelliteViewButton.setOnClickListener(this);
        normalViewButton.toggle();
        panZoom.setOnClickListener(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        aEarthquakeData = receiveEarthquakeData();

        for (int i = 0; i < aEarthquakeData.size(); i++) {
            LatLng currentEarthquake = new LatLng(aEarthquakeData.get(i).getItemGeoLat(),aEarthquakeData.get(i).getItemGeoLong());

            EarthquakeDescriptionInformation currentEarthquakeDescription = aEarthquakeData.get(i).getItemDescription();

            String currentLocation = currentEarthquakeDescription.getEarthquakeLocation();

            mMap.addMarker(new MarkerOptions().position(currentEarthquake).title("Earthquake in " + currentLocation));
        }

    }

    @Override
    public void onClick(View arg0)
    {
        if (arg0 == s1Button)
        {
            mapViewSwitcher.showNext();
        }
        else
        if (arg0 == s2Button)
        {
            mapViewSwitcher.showPrevious();
        }
        else
        if (arg0 == normalViewButton)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else
        if (arg0 == terrainViewButton)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        else
        if (arg0 == hybridViewButton)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        else
        if (arg0 == satelliteViewButton)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

        if (panZoom.isChecked())
        {
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
        else
        {
            mMap.getUiSettings().setZoomControlsEnabled(false);
        }
    }

    public ArrayList<EarthquakeFeedItem> receiveEarthquakeData(){
        ArrayList<EarthquakeFeedItem> earthquakeList = (ArrayList<EarthquakeFeedItem>) getIntent().getSerializableExtra("earthquakeList");
        return earthquakeList;
    }

} // End of Map Activity
