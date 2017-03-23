package com.wingoku.countryapp.interfaces;

import com.wingoku.countryapp.BuildConfig;
import com.wingoku.countryapp.models.Country;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Umer on 2/25/2016.
 */
public interface RestCountriesAPI {

    String BASE_URL = "https://restcountries.eu/rest/v2/";

    /** Get list of country names and capitals to be shown in the list
     *
     * @return Retrofit Call object to be used in callback
     */
    @GET("all?fields=name;capital")
    Call<List<Country>> getListOfCountriesInTheWorld();

    /** Get info of a specific country, mentioned country name in "countryName"
     *
     * @param countryName name of the country to fetch details
     * @return Retrofit Call object to be used in callback
     */
    @GET("name/{countryName}")
    Call<List<Country>> getCountryByName(@Path("countryName") String countryName);

    class Factory {
        private static RestCountriesAPI restCountriesAPI;

        /**
         * Get Retrofit instance
         *
         * @return Returns RetroFit instance for Network Calls
         */
        public static RestCountriesAPI getInstance() {
            if (restCountriesAPI == null) {
                OkHttpClient.Builder okHttpClient_builder = new OkHttpClient().newBuilder();
                okHttpClient_builder.connectTimeout(10, TimeUnit.SECONDS);
                okHttpClient_builder.readTimeout(20, TimeUnit.SECONDS);

                // if the application is DEBUG build, then show the intercepted info from the server in logs
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    okHttpClient_builder.addInterceptor(interceptor);
                }

                // instantiate retroFit object for network calls
                Retrofit retrofit = new Retrofit.Builder()
                        .client(okHttpClient_builder.build())
                        .addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
                        .build();
                restCountriesAPI = retrofit.create(RestCountriesAPI.class);
                return restCountriesAPI;
            } else {
                return restCountriesAPI;
            }
        }
    }
}
