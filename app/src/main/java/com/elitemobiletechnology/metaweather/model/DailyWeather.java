package com.elitemobiletechnology.metaweather.model;

import com.google.gson.annotations.SerializedName;

public class DailyWeather {
    @SerializedName("applicable_date")
    private String date;
    @SerializedName("the_temp")
    private double temperature;
    @SerializedName("weather_state_name")
    private String condition;
    @SerializedName("predictability")
    private int precipitation;

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return String.valueOf((int)temperature) + (char) 0x00B0;
    }

    public String getCondition() {
        return condition;
    }

    public String getPrecipitation() {
        return String.valueOf(precipitation)+"%";
    }
}
