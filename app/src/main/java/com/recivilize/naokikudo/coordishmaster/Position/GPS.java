package com.recivilize.naokikudo.coordishmaster.Position;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class GPS implements LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {

    //変数
    private double latitude;
    private double longitude;
    private final int LOCATION_UPDATE_MIN_TIME = 1000;
    private final int LOCATION_UPDATE_DISTANCE = 10;
    public static final int REQUEST_LOCATION = 1;
    protected static double[] position;

    private Context context;
    private Activity activity;

    private LocationManager mLocationManager;


    public static ProgressDialog progressDialog;


    public GPS(Activity activity, Context context) {
        position = new double[2];
        this.context = context;
        this.activity = activity;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();

        //位置情報が取得できたら止める
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.removeUpdates(this);
            position[0] = latitude;
            position[1] = longitude;
            progressDialog.dismiss();
            Log.d("coordishmaster.GPS", "Got Position");
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

//    public double getLatitude() {
//        return latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }

    public void startGPS() { //まぁネットワークから取ってんだけどね

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //位置情報取得開始
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME,
                    LOCATION_UPDATE_DISTANCE,
                    this
            );

            //プログレスバーをセット
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("位置情報を取得しています");
            progressDialog.setCancelable(false);
            progressDialog.show();

        } else {
            //パーミッションがなかったらリクエストする
            requestPermission();
        }
    }


    public double[] getPosition() {
        return position;
    }


    public void requestPermission() {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("coordishmaster.GPS", "Work here");
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startGPS();
                } else {
                    requestPermission();
                }
                break;
            }

        }
    }
}
