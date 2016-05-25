package com.recivilize.naokikudo.coordishmaster.Position;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.recivilize.naokikudo.coordishmaster.R;

/**
 * Created by NaokiKudo on 16/05/24.
 */
public class GPS implements LocationListener {

    //変数
    private double latitude;
    private double longitude;

    private final int LOCATION_UPDATE_MIN_TIME = 1000;
    private final int LOCATION_UPDATE_DISTANCE = 10;

    private Context context;

    private LocationManager mLocationManager;
    private TextView textView;

    ProgressDialog progressDialog;


    public GPS(Context context) {
        this.context = context;
        textView = (TextView) ((com.recivilize.naokikudo.coordishmaster.Activity.MainActivity) context).findViewById(R.id.textView);
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }



    @Override
    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        textView.setText("Latitude:" + latitude + " Longitude:" + longitude);

        //位置情報が取得できたらGPSを止める
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.removeUpdates(this);
            progressDialog.dismiss();
            Snackbar.make(textView, "位置情報の取得が完了しました", Snackbar.LENGTH_SHORT).show();
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void startGPS (){

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME,
                    LOCATION_UPDATE_DISTANCE,
                    this
            );
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("位置情報を取得しています");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            Snackbar.make(textView, "Permission Denied", Snackbar.LENGTH_SHORT).show();
        }
    }

    public  static boolean checkPermission (Context context){
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
