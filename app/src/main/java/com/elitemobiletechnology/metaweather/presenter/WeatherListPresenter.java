package com.elitemobiletechnology.metaweather.presenter;

import com.elitemobiletechnology.metaweather.model.DailyWeather;
import com.elitemobiletechnology.metaweather.view.WeatherListAdapter;

import java.util.List;

public interface WeatherListPresenter {
    int getCount();

    Object getItem(int position);

    long getItemId(int position);

    void onBindViewHolder(WeatherListAdapter.ViewHolder holder, int position);

    void setWeatherList(List<DailyWeather> weatherList);
}
