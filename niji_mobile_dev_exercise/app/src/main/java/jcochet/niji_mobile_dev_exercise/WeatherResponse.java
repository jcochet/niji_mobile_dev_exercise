package jcochet.niji_mobile_dev_exercise;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Classe réprésentant la réponse de l'API de météo (OpenWeatherMap)
 *
 * @author Julien Cochet
 */
public class WeatherResponse {

    @SerializedName("coord")
    public Coord coord;

    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<>();

    @SerializedName("base")
    public String base;

    @SerializedName("main")
    public Main main;

    @SerializedName("visibility")
    public float visibility;

    @SerializedName("wind")
    public Wind wind;

    @SerializedName("clouds")
    public Clouds clouds;

    @SerializedName("dt")
    public float dt;

    @SerializedName("sys")
    public Sys sys;

    @SerializedName("timezone")
    public float timezone;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("cod")
    public float cod;
}

class Coord {

    @SerializedName("lon")
    public float lon;

    @SerializedName("lat")
    public float lat;
}

class Weather {

    @SerializedName("id")
    public int id;

    @SerializedName("main")
    public String main;

    @SerializedName("description")
    public String description;

    @SerializedName("icon")
    public String icon;
}

class Main {

    @SerializedName("temp")
    public float temp;

    @SerializedName("feels_like")
    public float feels_like;

    @SerializedName("temp_min")
    public float temp_min;

    @SerializedName("temp_max")
    public float temp_max;

    @SerializedName("pressure")
    public float pressure;

    @SerializedName("humidity")
    public float humidity;
}

class Wind {

    @SerializedName("speed")
    public float speed;

    @SerializedName("deg")
    public float deg;
}

class Clouds {

    @SerializedName("all")
    public float all;
}

class Sys {

    @SerializedName("type")
    public int type;

    @SerializedName("id")
    public int id;

    @SerializedName("country")
    public String country;

    @SerializedName("sunrise")
    public long sunrise;

    @SerializedName("sunset")
    public long sunset;
}