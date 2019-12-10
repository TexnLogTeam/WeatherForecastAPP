package com.example.weatherforecastapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.androdocs.httprequest.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    DatabseHelper myDb;
    //klidi gia api
    //  
    String CITY = "Serres";
    String API = "2e5293a7a1ec02512b976aaceed21885";

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;


    public Button Button1;
    public Button btnAdd;




    public void init() {
        Button1 = (Button)findViewById(R.id.but1);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startUp = new Intent(MainActivity.this,HistorySelectionScreen.class);

                startActivity(startUp);
            }
        });

    }
   // SearchView simpleSearchView = (SearchView) findViewById(R.id.searchCity);
    //CharSequence query = simpleSearchView.getQuery();



    public void viewAllHistory() {
        Button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      Cursor res = myDb.getAllHistory();
                      if (res.getCount() == 0 ) {
                          // Show message

                          showMessage("Error","No weather history data found ");
                          return ;
                      }

                      StringBuffer buffer = new StringBuffer();
                      while (res.moveToNext()) {
                          buffer.append("ID :" + res.getString(0)+"\n");
                         // buffer.append("City :" + res.getString(1)+"\n");
                          buffer.append("Date :" + res.getString(2)+"\n");
                          buffer.append("Temperature :" + res.getString(3)+"\n");
                          buffer.append("Minimum Temperature :" + res.getString(4)+"\n");
                          buffer.append("Maximum Temperature :" + res.getString(5)+"\n");
                          buffer.append("Wind :" + res.getString(6)+"\n");
                          buffer.append("Preassure :" + res.getString(7)+"\n");
                          buffer.append("Humidity :" + res.getString(8)+"\n\n");

                      }

                      //Show all history data
                        showMessage("History Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();



    }




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            init();
            addressTxt = findViewById(R.id.address);
            updated_atTxt = findViewById(R.id.updated_at);
            statusTxt = findViewById(R.id.status);
            tempTxt = findViewById(R.id.temp);
            temp_minTxt = findViewById(R.id.temp_min);
            temp_maxTxt = findViewById(R.id.temp_max);
            sunriseTxt = findViewById(R.id.sunrise);
            sunsetTxt = findViewById(R.id.sunset);
            windTxt = findViewById(R.id.wind);
            pressureTxt = findViewById(R.id.pressure);
            humidityTxt = findViewById(R.id.humidity);

            viewAllHistory();

            new weatherTask().execute();

            myDb=new DatabseHelper(this);

           // startActivity(new Intent(this, DatabseHelper.class));



            AddData();








        }

    public void AddData() {

        btnAdd.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.insertData(updated_atTxt.getText().toString(),
                                tempTxt.getText().toString(),
                                temp_minTxt.getText().toString(),
                                temp_maxTxt.getText().toString(),
                                windTxt.getText().toString(),
                                pressureTxt.getText().toString(),
                                humidityTxt.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data NOT Inserted", Toast.LENGTH_LONG).show();

                        }


                    }
                }
        );
    }



        class weatherTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                /* Showing the ProgressBar, Making the main design GONE */
                findViewById(R.id.loader).setVisibility(View.VISIBLE);
                findViewById(R.id.mainContainer).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.GONE);
            }


            protected String doInBackground(String... args) {
              //  CITY= query.toString();
                String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
                return response;
            }

            @Override
            protected void onPostExecute(String result) {


                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONObject main = jsonObj.getJSONObject("main");
                    JSONObject sys = jsonObj.getJSONObject("sys");
                    JSONObject wind = jsonObj.getJSONObject("wind");
                    JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                    Long updatedAt = jsonObj.getLong("dt");
                    String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                    String temp = main.getString("temp") + "°C";
                    String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                    String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                    String pressure = main.getString("pressure");
                    String humidity = main.getString("humidity");

                    Long sunrise = sys.getLong("sunrise");
                    Long sunset = sys.getLong("sunset");
                    String windSpeed = wind.getString("speed");
                    String weatherDescription = weather.getString("description");

                    String address = jsonObj.getString("name") + ", " + sys.getString("country");


                    /* Populating extracted data into our views */
                    addressTxt.setText(address);
                    updated_atTxt.setText(updatedAtText);
                    statusTxt.setText(weatherDescription.toUpperCase());
                    tempTxt.setText(temp);
                    temp_minTxt.setText(tempMin);
                    temp_maxTxt.setText(tempMax);
                    sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                    sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                    windTxt.setText(windSpeed);
                    pressureTxt.setText(pressure);
                    humidityTxt.setText(humidity);





                    /* Views populated, Hiding the loader, Showing the main design */

                    findViewById(R.id.loader).setVisibility(View.GONE);
                    findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    findViewById(R.id.loader).setVisibility(View.GONE);
                    findViewById(R.id.errorText).setVisibility(View.VISIBLE);
                }


                myDb.insertData(
                        updated_atTxt.getText().toString(),
                        tempTxt.getText().toString(),
                        temp_minTxt.getText().toString(),
                        temp_maxTxt.getText().toString(),
                        windTxt.getText().toString(),
                        pressureTxt.getText().toString(),
                        humidityTxt.getText().toString());







            }
        }
    }
