package com.vladikavkaz.taxi.taxivladikavkaz;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import static android.content.Context.LOCATION_SERVICE;

public class GetMyLocation implements LocationListener {

     Location geoPoint;
    public   GetMyLocation(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            //LocationListener locationListener = new GetMyLocation();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 , 1, this);
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 1000 , 1,
                        this);
        geoPoint = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            this.geoPoint = location;
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            this.geoPoint = location;
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

    public Location getLocation(){
        return geoPoint;
    }

}
