package io.github.ryanhoo.firFlight.data;

import android.content.Context;
import io.github.ryanhoo.firFlight.FlightApplication;
import io.github.ryanhoo.firFlight.data.source.remote.api.ApiService;
import io.github.ryanhoo.firFlight.data.source.remote.api.RESTFulApiService;
import io.github.ryanhoo.firFlight.network.RetrofitClient;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:48 AM
 * Desc: Injection
 */
public class Injection {

    public static Context provideContext() {
        return FlightApplication.getInstance();
    }

    public static RESTFulApiService provideRESTfulApi() {
        return RetrofitClient.defaultInstance().create(RESTFulApiService.class);
    }

    public static ApiService provideApi() {
        return RetrofitClient.defaultInstancePgy().create(ApiService.class);
    }
}
