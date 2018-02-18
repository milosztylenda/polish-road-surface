package mtylenda.polishroadsurface.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


public class LiveDataMapImagePathViewModel extends ViewModel {

    private MutableLiveData<String> mapImagePath = new MutableLiveData<>();

    public MutableLiveData<String> getMapImagePath() {
        return mapImagePath;
    }
}
