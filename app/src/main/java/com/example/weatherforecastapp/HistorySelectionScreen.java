package com.example.weatherforecastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;

public class HistorySelectionScreen extends AppCompatActivity {

    EditText cityContainer;
    Button btn1;
    String API = "2e5293a7a1ec02512b976aaceed21885";

    protected String doInBackground(String result) {

    String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + result + "&units=metric&appid=" + API);
    return response;
       }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_selection_screen);


        cityContainer = (EditText)findViewById(R.id.cityTxt);
        btn1 = (Button) findViewById(R.id.firstBtn);

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String res = cityContainer.getText().toString();

                        doInBackground(res);


                        if (res == null){

                            Toast.makeText(HistorySelectionScreen.this, "That is not a city's name. Please try again.", Toast.LENGTH_LONG).show();

                        }
                        else{
                                Intent startUp = new Intent(HistorySelectionScreen.this, MainActivity.class);

                                startActivity(startUp);
                            }
                    }
                }
        );





    }


}
