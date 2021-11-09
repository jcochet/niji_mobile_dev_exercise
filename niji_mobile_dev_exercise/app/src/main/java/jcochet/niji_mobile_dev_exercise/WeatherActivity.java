package jcochet.niji_mobile_dev_exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Activité affichant le temps et la température de Rennes
 *
 * @author Julien Cochet
 */
public class WeatherActivity extends AppCompatActivity {

    private final static String API_URL = "https://api.openweathermap.org/";
    private final static String ZIP = "35700,fr";
    private final static String UNITS = "metric";
    private final static String LANG = "fr";
    private final static String ICON_PATH = "https://openweathermap.org/img/w/";

    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        TextView tvWeather = (TextView) findViewById(R.id.weather);
        ImageView ivWeather = (ImageView) findViewById(R.id.weatherIcon);
        TextView tvTemperature = (TextView) findViewById(R.id.temperature);
        TextView tvLocalisation = (TextView) findViewById(R.id.localisation);

        try {
            apiKey = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.google.android.geo.API_KEY");
            writeCurrentWeatherData(tvWeather, ivWeather, tvTemperature, tvLocalisation);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Affiche les différentes informations à l'écran
     *
     * @param tvWeather      TextView affichant le temps
     * @param ivWeather      ImageView affichant l'icône du temps
     * @param tvTemperature  TextView affichant la température
     * @param tvLocalisation TextView affichant la localisation
     */
    private void writeCurrentWeatherData(TextView tvWeather, ImageView ivWeather, TextView tvTemperature, TextView tvLocalisation) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(ZIP, apiKey, UNITS, LANG);
        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    ArrayList<Weather> weathers = weatherResponse.weather;
                    if (!weathers.isEmpty()) {
                        Weather weather = weathers.get(0);
                        tvWeather.setText(weather.description);
                        Picasso.get().load(ICON_PATH + weather.icon + ".png").into(ivWeather);
                    }
                    tvTemperature.setText(weatherResponse.main.temp + "°C");
                    tvLocalisation.setText(weatherResponse.name);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                tvLocalisation.setText(t.getMessage());
            }
        });
    }
}