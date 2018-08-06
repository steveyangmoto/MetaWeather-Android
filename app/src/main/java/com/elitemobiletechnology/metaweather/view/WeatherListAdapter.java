package com.elitemobiletechnology.metaweather.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elitemobiletechnology.metaweather.R;
import com.elitemobiletechnology.metaweather.model.DailyWeather;
import com.elitemobiletechnology.metaweather.presenter.WeatherListPresenter;
import com.elitemobiletechnology.metaweather.presenter.WeatherListPresenterImpl;

import java.util.List;

public class WeatherListAdapter extends BaseAdapter{
    LayoutInflater inflater;
    WeatherListPresenter presenter;

    public WeatherListAdapter(Context context, List<DailyWeather> weatherList){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        presenter = new WeatherListPresenterImpl(weatherList);
    }
    @Override
    public int getCount() {
        return presenter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return presenter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return presenter.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        WeatherListAdapter.ViewHolder holder;
        if(view==null){
            view = inflater.inflate(R.layout.weather_list_row,parent,false);
            holder = new WeatherListAdapter.ViewHolder((ViewGroup)view);
            view.setTag(holder);
        }else{
            holder = (WeatherListAdapter.ViewHolder)view.getTag();
        }
        presenter.onBindViewHolder(holder,position);
        return view;
    }

    public void setWeatherList(List<DailyWeather> weatherList){
        presenter.setWeatherList(weatherList);
        notifyDataSetChanged();
    }

    public static class ViewHolder{
        TextView tvDate;
        TextView tvCondition;
        TextView tvtemp;
        TextView tvPrecip;
        public ViewHolder(ViewGroup vg){
            tvDate = (TextView)vg.findViewById(R.id.tvDate);
            tvCondition = (TextView)vg.findViewById(R.id.tvCondition);
            tvtemp = (TextView)vg.findViewById(R.id.tvTemperature);
            tvPrecip = (TextView)vg.findViewById(R.id.tvPrecipitation);
        }

        public void setDate(String date){
            this.tvDate.setText(date);
        }

        public void setCondition(String condition){
            this.tvCondition.setText(condition);
        }

        public void setTemperature(String temperature){
            this.tvtemp.setText(temperature);
        }

        public void setPrecipitation(String precip){
            this.tvPrecip.setText(precip);
        }
    }
}
