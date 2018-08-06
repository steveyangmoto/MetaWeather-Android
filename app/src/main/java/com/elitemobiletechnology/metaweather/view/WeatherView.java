package com.elitemobiletechnology.metaweather.view;

import com.elitemobiletechnology.metaweather.model.DailyWeather;

import java.util.List;

public interface WeatherView {
    void onWeatherUpdate(List<DailyWeather> dailyWeatherList);
}
