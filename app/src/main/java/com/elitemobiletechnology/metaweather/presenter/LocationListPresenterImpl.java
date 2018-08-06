package com.elitemobiletechnology.metaweather.presenter;

import com.elitemobiletechnology.metaweather.model.Location;
import com.elitemobiletechnology.metaweather.view.LocationListAdapter;

import java.util.List;


public class LocationListPresenterImpl implements LocationListPresenter{
    List<Location> locationList;

    public LocationListPresenterImpl(List<Location> locations){
        this.locationList = locations;
    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return locationList.get(position).hashCode();
    }

    @Override
    public void onBindViewHolder(LocationListAdapter.ViewHolder holder,int position){
        Location location = locationList.get(position);
        holder.setId(location.getWoeid());
        holder.setTitle(location.getTitle());
        holder.setType(location.getLocation_type());
    }

}
