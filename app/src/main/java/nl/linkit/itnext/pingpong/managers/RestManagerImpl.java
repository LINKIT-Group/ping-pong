package nl.linkit.itnext.pingpong.managers;

import android.support.compat.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: efe.cem.kocabas
 * Date: 24/11/2016.
 */

public class RestManagerImpl implements RestManager {

    private PingpongApi pingpongApi;
    private static final String TEMP_URL = "https://temp.url";

    public RestManagerImpl() {
    }

    @Override
    public PingpongApi getPingpongApi() {

        if (pingpongApi == null) {

            pingpongApi = createRetrofitService(RestManager.PingpongApi.class, TEMP_URL, GsonConverterFactory.create());
        }

        return pingpongApi;
    }

    private <T> T createRetrofitService(Class<T> apiClass, String endPoint, Converter.Factory converterFactory) {

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(endPoint);

        if (BuildConfig.DEBUG) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            retrofitBuilder.client(new OkHttpClient().newBuilder().addInterceptor(interceptor).build());
        }

        return retrofitBuilder.build().create(apiClass);

    }


}
