package jcochet.niji_mobile_dev_exercise;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface pour les appels vers l'API de météo (OpenWeatherMap)
 *
 * @author Julien Cochet
 */
public interface WeatherService {

    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("zip") String zip, @Query("appid") String appid, @Query("units") String units, @Query("lang") String lang);
}
