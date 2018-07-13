package mtylenda.polishroadsurface.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import mtylenda.polishroadsurface.R;
import mtylenda.polishroadsurface.model.LiveDataLocationViewModel;
import mtylenda.polishroadsurface.model.LiveDataMapImagePathViewModel;
import mtylenda.polishroadsurface.service.MapImageDownloader;

public class MainActivity extends AppCompatActivity {

    private static final String MAP_IMAGE_VIEW_STATE = "MapImageViewState";

    private ProgressBar progressBar;
    private SubsamplingScaleImageView mapImageView;

    private final Observer<String> mapImagePathObserver = new Observer<String>() {

        @Override
        public void onChanged(@Nullable String mapImagePath) {
            progressBar.setVisibility(View.GONE);
            mapImageView.setImage(ImageSource.uri(mapImagePath), mapImageViewState);
        }
    };
    private ImageViewState mapImageViewState;

    private Observer<Location> locationObserver = new Observer<Location>() {

        @Override
        public void onChanged(@Nullable Location location) {
            Log.i(getLocalClassName(),"Location updated: " + location);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        mapImageView = findViewById(R.id.imageView);

        LiveDataMapImagePathViewModel mapImagePathViewModel = ViewModelProviders.of(this).get(LiveDataMapImagePathViewModel.class);
        mapImagePathViewModel.getMapImagePath().observe(this, mapImagePathObserver);

        LiveDataLocationViewModel locationViewModel = ViewModelProviders.of(this).get(LiveDataLocationViewModel.class);
        locationViewModel.getLocation(this).observe(this, locationObserver);

        if (savedInstanceState != null) {
            mapImageViewState = (ImageViewState) savedInstanceState.getSerializable(MAP_IMAGE_VIEW_STATE);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        new MapImageDownloader(mapImagePathViewModel).download();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ImageViewState state = mapImageView.getState();
        if (state != null) {
            outState.putSerializable(MAP_IMAGE_VIEW_STATE, mapImageView.getState());
        }
    }
}
