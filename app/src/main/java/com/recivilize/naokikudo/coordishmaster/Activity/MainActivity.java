package com.recivilize.naokikudo.coordishmaster.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.recivilize.naokikudo.coordishmaster.Position.GPS;
import com.recivilize.naokikudo.coordishmaster.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button button;
    GPS gps;
    double[] position = new double[2];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.gpsButton);
        button.setOnClickListener(this);
    }

    public void get() {
        if (gps == null) {
            gps = new GPS(this, this);
        }
        gps.startGPS();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gpsButton:
                get();
                break;
        }
    }
}
