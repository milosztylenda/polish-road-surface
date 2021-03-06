package mtylenda.polishroadsurface.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


public class CacheControlResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "max-age=60")
                .build();
    }
}
