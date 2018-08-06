package com.elitemobiletechnology.metaweather.model;

public interface WeatherApi {
    Location[] getLocationByLatLong(double lattitude, double longitude);
    Location[] getLocationByName(String keyword);
    DailyWeather[] getFiveDayForcast(String woeid);
}
