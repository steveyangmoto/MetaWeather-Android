package com.elitemobiletechnology.metaweather.model;

import android.support.annotation.NonNull;

import java.util.List;

public interface MetaWeatherApi {

    interface OnLocationResultListener{
        void onLocationResult(List<Location> locations);
        void onError(GenericWeatherApiError error);
    }

    interface  OnDailyWeatherUpdateListener{
        void onDailyWeatherUpdate(List<DailyWeather> forcasts);
        void onError(GenericWeatherApiError error);
    }

    enum GenericWeatherApiError{
        NETWORK_ERROR,UNKNOWN_ERROR
    }

    void getLocationsByLatLong(double lattitude, double longitude, @NonNull OnLocationResultListener resultListener);
    void getLocationByName(String keyword, @NonNull OnLocationResultListener resultListener);
    void getFiveDayForcast(String woeid, @NonNull OnDailyWeatherUpdateListener listener);
    void destroy();

}
