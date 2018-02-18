package mtylenda.polishroadsurface.ui;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.io.IOException;

import mtylenda.polishroadsurface.PRSApplication;
import mtylenda.polishroadsurface.R;
import mtylenda.polishroadsurface.model.LiveDataMapImagePathViewModel;
import mtylenda.polishroadsurface.service.FileFinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {


    private SubsamplingScaleImageView mapImageView;

    private final Observer<String> mapImagePathObserver = new Observer<String>() {

        @Override
        public void onChanged(@Nullable String mapImagePath) {
            mapImageView.setImage(ImageSource.uri(mapImagePath));
        }
    };
    private LiveDataMapImagePathViewModel mapImagePathViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapImageView = findViewById(R.id.imageView);

        mapImagePathViewModel = ViewModelProviders.of(this).get(LiveDataMapImagePathViewModel.class);
        mapImagePathViewModel.getMapImagePath().observe(this, mapImagePathObserver);

        downloadMapImage();
    }

    private void downloadMapImage() {
        Request request = new Request.Builder()
                //.url("http://publicobject.com/helloworld.txt")
                .url("http://ssc.siskom.waw.pl/mapa-nawierzchni/mapa-nawierzchnia.png")
                .build();

        PRSApplication.client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @TargetApi(19)
            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println("####################### LENGTH = " + responseBody.bytes().length);
                    System.out.println(responseBody.getClass());

                    final File cachedImageFile = new FileFinder().findFile(PRSApplication.client.cache().directory());
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$ FILE = " + cachedImageFile + " thread = " + Thread.currentThread());

                    // pass cachedFile to mapImageView, see toptal post on threading.
                    // https://proandroiddev.com/architecture-components-modelview-livedata-33d20bdcc4e9
                    // TODO move to github
                    // TODO figure out why okhttp returns cached response without any HTTP request
                    // ostatnia mapa z 9 lutego

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mapImagePathViewModel.getMapImagePath().setValue(cachedImageFile.getAbsolutePath());

                        }
                    });
                }
            }
        });
    }
}
