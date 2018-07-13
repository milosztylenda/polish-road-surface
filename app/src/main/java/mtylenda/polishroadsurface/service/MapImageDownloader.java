package mtylenda.polishroadsurface.service;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;

import mtylenda.polishroadsurface.PRSApplication;
import mtylenda.polishroadsurface.model.LiveDataMapImagePathViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MapImageDownloader {

    private LiveDataMapImagePathViewModel mapImagePathViewModel;

    public MapImageDownloader(LiveDataMapImagePathViewModel mapImagePathViewModel) {
        this.mapImagePathViewModel = mapImagePathViewModel;
    }

    public void download() {
        Request request = new Request.Builder()
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
