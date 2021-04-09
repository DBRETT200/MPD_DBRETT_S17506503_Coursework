package org.me.gcu.dbrett_s1706053_coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

//Daniel Brett
//S1706503

public class DatePickerActivity extends AppCompatActivity {
    DatePickerDialog dateToPicker;
    DatePickerDialog dateFromPicker;
    EditText editDateToText;
    EditText editDateFromText;
    Button getDataButton;
    TextView viewData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        viewData=(TextView)findViewById(R.id.textView1);
        editDateToText=(EditText) findViewById(R.id.editText1);
        editDateToText.setInputType(InputType.TYPE_NULL);
        editDateToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // dateTo picker dialog
                dateToPicker = new DatePickerDialog(DatePickerActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                editDateToText.setText(selectedMonth + "/" + (selectedYear + 1) + "/" + selectedYear);
                            }
                        }, year, month, day);
                dateToPicker.show();
            }
        });

        editDateFromText=(EditText) findViewById(R.id.editText2);
        editDateFromText.setInputType(InputType.TYPE_NULL);
        editDateFromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // dateTo picker dialog
                dateFromPicker = new DatePickerDialog(DatePickerActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                editDateFromText.setText(selectedMonth + "/" + (selectedYear + 1) + "/" + selectedYear);
                            }
                        }, year, month, day);
                dateFromPicker.show();
            }
        });
        getDataButton=(Button)findViewById(R.id.button1);
        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ArrayList<EarthquakeFeedItem> earthquakeDataFromMain = receiveEarthquakeData();

                ArrayList<EarthquakeDescriptionInformation> earthquakeDescriptionWithinDate = new ArrayList<>();
                for (int i = 0; i < earthquakeDataFromMain.size(); i++) {
                    if(earthquakeDataFromMain.get(i).getItemPublicationDate().equals(editDateFromText)){
                        EarthquakeDescriptionInformation earthquakeDetails = earthquakeDataFromMain.get(i).getItemDescription();
                        earthquakeDescriptionWithinDate.add(earthquakeDetails);
                    }
                }

                float lastMagnitudeValue = 0.00f;
                String largestEarthquake = "";
                 for(int i = 0; i < earthquakeDescriptionWithinDate.size(); i++){
                     float currentMagnitudeValue = earthquakeDescriptionWithinDate.get(i).getEarthquakeMagnitude();
                      if(currentMagnitudeValue > lastMagnitudeValue){
                        largestEarthquake = "Location: " + earthquakeDescriptionWithinDate.get(i).getEarthquakeLocation() + System.lineSeparator()
                                + " Magnitude:" + earthquakeDescriptionWithinDate.get(i).getEarthquakeMagnitude();
                        lastMagnitudeValue = currentMagnitudeValue;
                      }
                 }


                viewData.setText("Largest Magnitude Earthquake: " + largestEarthquake);
            }
        });
    }

    public ArrayList<EarthquakeFeedItem> receiveEarthquakeData(){
        ArrayList<EarthquakeFeedItem> earthquakeList = (ArrayList<EarthquakeFeedItem>) getIntent().getSerializableExtra("earthquakeList");
        return earthquakeList;
    }
}