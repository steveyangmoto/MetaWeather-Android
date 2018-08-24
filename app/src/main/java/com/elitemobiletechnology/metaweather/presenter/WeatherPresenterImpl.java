package com.elitemobiletechnology.metaweather.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.elitemobiletechnology.metaweather.MwConstants;
import com.elitemobiletechnology.metaweather.model.DailyWeather;
import com.elitemobiletechnology.metaweather.model.MetaWeatherApi;
import com.elitemobiletechnology.metaweather.model.MetaWeatherApiImpl;
import com.elitemobiletechnology.metaweather.view.WeatherView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WeatherPresenterImpl implements WeatherPresenter{
    private static final String TAG = "WeatherPresenterImpl";
    private WeatherView weatherView;
    private MetaWeatherApi metaWeatherApi;


    public WeatherPresenterImpl(WeatherView weatherView){
        this.weatherView = weatherView;
        this.metaWeatherApi = new MetaWeatherApiImpl();
    }
    @Override
    public void onActivityCreate(Intent intent) {
        final String locationId = intent.getStringExtra(MwConstants.LOCATION_ID);
        if(locationId!=null) {
            metaWeatherApi.getFiveDayForcast(locationId, new MetaWeatherApi.OnDailyWeatherUpdateListener() {
                @Override
                public void onDailyWeatherUpdate(List<DailyWeather> weatherList) {
                    weatherView.onWeatherUpdate(weatherList);

                }

                @Override
                public void onError(MetaWeatherApi.GenericWeatherApiError error) {
                    weatherView.onWeatherUpdate(new ArrayList<DailyWeather>());
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        weatherView = null;
        metaWeatherApi.destroy();
    }
}
