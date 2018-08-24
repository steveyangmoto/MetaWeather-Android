package com.elitemobiletechnology.metaweather;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.elitemobiletechnology.metaweather.model.DailyWeather;
import com.elitemobiletechnology.metaweather.presenter.WeatherPresenter;
import com.elitemobiletechnology.metaweather.presenter.WeatherPresenterImpl;
import com.elitemobiletechnology.metaweather.view.WeatherListAdapter;
import com.elitemobiletechnology.metaweather.view.WeatherView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends Activity implements WeatherView{
    private WeatherPresenter presenter;
    private ListView weatherListView;
    private WeatherListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        weatherListView = (ListView)findViewById(R.id.weatherForecastList);
        View headerView = inflater.inflate(R.layout.weather_list_header,weatherListView,false);
        weatherListView.addHeaderView(headerView);
        weatherListView.setEmptyView(findViewById(R.id.empty_list_view));
        adapter = new WeatherListAdapter(this.getApplicationContext(), new ArrayList<DailyWeather>());
        weatherListView.setAdapter(adapter);
        presenter = new WeatherPresenterImpl(this);
        presenter.onActivityCreate(getIntent());
    }

    @Override
    public void onWeatherUpdate(List<DailyWeather> dailyWeatherList) {
        adapter.setWeatherList(dailyWeatherList);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        presenter.onDestroy();
    }
}
