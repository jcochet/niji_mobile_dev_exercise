package jcochet.niji_mobile_dev_exercise;

import android.os.Debug;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Optional;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface WeatherService2 {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherByZip(@Query("zip") String zip, @Query("appid") String appid, @Query("units") String units, @Query("lang") String lang);
}

public class WeatherAPI {

    private final static String API_URL = "https://api.openweathermap.org/";
    private final static String API_ID = "02b0ee5aa3573ae225a441e9d73bf37d";
    //private final static String ZIP = "35700,fr";
    private final static String UNITS = "metric";
    private final static String LANG = "fr";

    private WeatherService2 service;

    public WeatherAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(WeatherService2.class);
    }

    public Optional<WeatherResponse> getCurrentWeather(String zip) {
        Call<WeatherResponse> call = service.getCurrentWeatherByZip(zip, API_ID, UNITS, LANG);

        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Log.e("OpenWeatherMap", t.getMessage());
            }
        });


        if(call.isExecuted()) {
            try {
                System.out.println("loul");
                return Optional.of(call.clone().execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("aie");
        return Optional.empty();
    }

}