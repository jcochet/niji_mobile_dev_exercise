package jcochet.niji_mobile_dev_exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.os.Debug;
import android.widget.TextView;

import java.io.IOException;
import java.util.Optional;

/**
 * Activité affichant la température d'un lieu
 *
 * @author Julien Cochet
 */
public class TemperatureActivity extends AppCompatActivity {

    private final static String API_URL = "https://api.openweathermap.org/";
    private final static String API_ID = "02b0ee5aa3573ae225a441e9d73bf37d";
    private final static String ZIP = "35700,fr";
    private final static String UNITS = "metric";
    private final static String LANG = "fr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        TextView tvTemperature = (TextView) findViewById(R.id.temperature);
        TextView tvLocalisation = (TextView) findViewById(R.id.localisation);
        setCurrentInfo(tvTemperature, tvLocalisation);
    }

    /**
     * Affiche la température et la localisation
     * @param tvTemperature TextView affichant la température
     * @param tvLocalisation TextView affichant la localisation
     */
    private void setCurrentInfo(TextView tvTemperature, TextView tvLocalisation) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(ZIP, API_ID, UNITS, LANG);
        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    tvTemperature.setText(weatherResponse.main.temp + "°C");
                    tvLocalisation.setText(weatherResponse.name);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                tvTemperature.setText("");
                tvLocalisation.setText(t.getMessage());
            }
        });
    }
}