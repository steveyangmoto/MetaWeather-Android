package com.elitemobiletechnology.metaweather.presenter;

import com.elitemobiletechnology.metaweather.model.DailyWeather;
import com.elitemobiletechnology.metaweather.view.WeatherListAdapter;

import java.util.List;

public class WeatherListPresenterImpl implements WeatherListPresenter{
    private List<DailyWeather> weatherList;
    public WeatherListPresenterImpl(List<DailyWeather> weatherList){
        this.weatherList = weatherList;

    }
    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return weatherList.get(position).hashCode();
    }

    @Override
    public void onBindViewHolder(WeatherListAdapter.ViewHolder holder, int position) {
        DailyWeather oneDayWeather = weatherList.get(position);
        holder.setDate(oneDayWeather.getDate());
        holder.setCondition(oneDayWeather.getCondition());
        holder.setPrecipitation(oneDayWeather.getPrecipitation());
        holder.setTemperature(oneDayWeather.getTemperature());
    }

    @Override
    public void setWeatherList(List<DailyWeather> weatherList){
        this.weatherList = weatherList;

    }

}
