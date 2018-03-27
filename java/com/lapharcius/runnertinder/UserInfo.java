package com.lapharcius.runnertinder;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Christopher on 3/13/2018.
 */

public class UserInfo extends Activity {
    EditText zip;

    LocationManager mLocationManager;
    UserLocationListener userListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        try {
            LocationManager locationManager =
                    (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Log.i("LOGMESSAGE", "Network provider enabled!");
            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Log.i("LOGMESSAGE", "GPS provider enabled!");
            } else {
                Log.i("LOGMESSAGE", "No providers enabled!");
                EditText zip = (EditText) findViewById(R.id.addressText);
                zip.setText("11209");
            }

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location currentLocation =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (currentLocation == null) {
                    // TODO: Consider calling
//    ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                          int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
                    userListener = new UserLocationListener();
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            (long) 10000.0,
                            (float) 10000.0, userListener);
                }
                Geocoder g = new Geocoder(getApplicationContext(),
                        new Locale(Locale.ENGLISH.toString(), Locale.US.toString()));
                List<Address> a = g.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                Log.i("LOGMESSAGE", a.get(0).getPostalCode().toString());
                EditText zip = (EditText) findViewById(R.id.addressText);
                zip.setText(a.get(0).getPostalCode());
                locationManager.removeUpdates(userListener);
            }
        }
        catch (SecurityException se)
        {
            Log.i("LOGMESSAGE", se.getMessage());
        }
        catch (IOException ioe)
        {
            Log.i("LOGMESSAGE", ioe.getMessage());
        }

    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        try {
            for (String provider : providers) {
                Log.i("LOGMESSAGE", "Provider == " + provider);
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    Log.i("LOGMESSAGE", "Found best last known location: " + l.toString());
                    bestLocation = l;
                }
            }
        }
        catch (SecurityException se)
        {
            Log.i("LOGMESSAGE", se.getMessage());
        }
        return bestLocation;
    }
    public void savePreferences(View v)
    {

    }
    class UserLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

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
    }
}
