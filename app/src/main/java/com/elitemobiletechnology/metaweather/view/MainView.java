package com.elitemobiletechnology.metaweather.view;

import com.elitemobiletechnology.metaweather.model.Location;

import java.util.List;

public interface MainView {
    void updateLocationList(List<Location> locations);
    void updateSearchHistory(List<String> keywords);
}
