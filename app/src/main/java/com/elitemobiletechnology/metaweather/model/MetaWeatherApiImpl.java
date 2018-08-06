package com.elitemobiletechnology.metaweather.model;

import android.net.Uri;
import android.util.Log;

import com.elitemobiletechnology.metaweather.MwConstants;
import com.elitemobiletechnology.metaweather.model.network.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

public class MetaWeatherApiImpl implements WeatherApi {
    private static final String TAG = "MetaWeatherApiImpl";
    private Gson gson;

    public MetaWeatherApiImpl(){
        gson = new Gson();
    }

    @Override
    public Location[] getLocationByLatLong(double lattitude, double longitude) {
        String url = MwConstants.META_WEATHER_HOST + "api/location/search/?lattlong=" + lattitude + "," + longitude;
        try {
            HttpRequest request = HttpRequest.get(url);
            if (request.ok()) {
                String body = request.body();
                try {
                    Location[] locations = gson.fromJson(body, Location[].class);
                    return locations;
                } catch (JsonSyntaxException ignore) {
                }
            }
        }catch (HttpRequest.HttpRequestException ex){
            Log.e(TAG,"http request exception: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Location[] getLocationByName(String keyword){
        String url = MwConstants.META_WEATHER_HOST + "api/location/search/?query=" + Uri.encode(keyword.trim());
        try {
            HttpRequest request = HttpRequest.get(url);
            if (request.ok()) {
                String body = request.body();
                try {
                    Location[] locations = gson.fromJson(body, Location[].class);
                    return locations;
                } catch (JsonSyntaxException ignore) {
                }
            }
        }catch (HttpRequest.HttpRequestException ex){
            Log.e(TAG,"http request exception: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public DailyWeather[] getFiveDayForcast(String woeid) {
        String url = MwConstants.META_WEATHER_HOST + "api/location/" + woeid;
        try {
            HttpRequest request = HttpRequest.get(url);
            if (request.ok()) {
                String body = request.body();
                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray jsonArr = json.getJSONArray("consolidated_weather");
                    if(jsonArr!=null) {
                        DailyWeather[] weatherList = gson.fromJson(jsonArr.toString(), DailyWeather[].class);
                        return weatherList;
                    }
                } catch (Exception ignore) {
                }
            }
        }catch (HttpRequest.HttpRequestException ex){
            Log.e(TAG,"http request exception: "+ex.getMessage());
        }
        return null;
    }
}
