package mtylenda.polishroadsurface.service;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationLiveData extends LiveData<Location> {

    private FusedLocationProviderClient fusedLocationProviderClient = null;
    private final Activity activity;

    public LocationLiveData(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.w("LocationLiveData", "Location permission not granted.");
            return;
        }
        FusedLocationProviderClient locationProviderClient = getFusedLocationProviderClient();

        LocationRequest locationRequest = LocationRequest.create();
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private FusedLocationProviderClient getFusedLocationProviderClient() {
        if (fusedLocationProviderClient == null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        }
        return fusedLocationProviderClient;
    }

    @Override
    protected void onInactive() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            setValue(locationResult.getLastLocation());
        }
    };
}
