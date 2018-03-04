package mtylenda.polishroadsurface;

import android.app.Application;

import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;

import java.io.File;

import mtylenda.polishroadsurface.service.CacheControlResponseInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by milosz on 09.12.17.
 */
public class PRSApplication extends Application {

    public static OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        BigImageViewer.initialize(FrescoImageLoader.with(this));

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(getCacheDir(), "okcache");
        System.out.println("CACHE DIR=" + cacheDirectory);
        Cache cache = new Cache(cacheDirectory, cacheSize);

        client = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new CacheControlResponseInterceptor())
                .build();

    }
}
