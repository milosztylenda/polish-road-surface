package mtylenda.polishroadsurface.model;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import mtylenda.polishroadsurface.service.LocationLiveData;


public class LiveDataLocationViewModel extends ViewModel {

    private LiveData<Location> locationLiveData = null;

    public LiveData<Location> getLocation(Activity activity) {
        if (locationLiveData == null) {
            locationLiveData = new LocationLiveData(activity);
        }
        return locationLiveData;
    }
}
