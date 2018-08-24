package com.elitemobiletechnology.metaweather.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.AdapterView;

import com.elitemobiletechnology.metaweather.MwConstants;
import com.elitemobiletechnology.metaweather.WeatherActivity;
import com.elitemobiletechnology.metaweather.WeatherApplication;
import com.elitemobiletechnology.metaweather.model.KeywordHelper;
import com.elitemobiletechnology.metaweather.model.MetaWeatherApi;
import com.elitemobiletechnology.metaweather.model.MetaWeatherApiImpl;
import com.elitemobiletechnology.metaweather.view.MainView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainPresenterImpl implements MainPresenter,LocationListener{
    private static final String TAG = "MainPresenterImpl";
    private final int REQUEST_FINE_LOCATION = 1234;
    private MainView mainView;
    private LocationManager mLocationManager;
    private WeakReference<Activity> activityWeakReference;
    private LocationManager locationManager;
    private MetaWeatherApi weatherApi;
    private KeywordHelper keywordHelper;
    private List<String> keywordList;

    public MainPresenterImpl(Activity activity, MainView mainView){
        this.mainView = mainView;
        activityWeakReference = new WeakReference<>(activity);
        weatherApi = new MetaWeatherApiImpl();
        keywordHelper = new KeywordHelper(WeatherApplication.getAppContext());
        keywordList = new ArrayList<>();
    }

    @Override
    public void onActivityCreate() {
        Set<String> keywords = keywordHelper.getKeywords();
        if(keywords!=null){
            keywordList.addAll(keywords);
            mainView.updateSearchHistory(keywordList);
        }
        permissionCheck();//using GPS to obtain location
    }

    @Override
    public void onSearchButtonClicked(final String keyword) {
        final boolean needToUpdate;
        if(!keywordList.contains(keyword)) {
            keywordHelper.saveKeyword(keyword);
            keywordList.add(keyword);
            needToUpdate = true;
        }else{
            needToUpdate = false;
        }
        weatherApi.getLocationByName(keyword, new MetaWeatherApi.OnLocationResultListener() {
            @Override
            public void onLocationResult(List<com.elitemobiletechnology.metaweather.model.Location> locations) {
                mainView.updateLocationList(locations);
                if(needToUpdate) {
                    mainView.updateSearchHistory(keywordList);
                }
            }

            @Override
            public void onError(MetaWeatherApi.GenericWeatherApiError error) {
                mainView.updateLocationList(new ArrayList<com.elitemobiletechnology.metaweather.model.Location>());
                if(needToUpdate) {
                    mainView.updateSearchHistory(keywordList);
                }
            }
        });
    }
    @Override
    public void onLocationListItemClicked(AdapterView adapterView,int position){
        com.elitemobiletechnology.metaweather.model.Location location = (com.elitemobiletechnology.metaweather.model.Location)adapterView.getAdapter().getItem(position);
        String locationId = location.getWoeid();

        if(activityWeakReference.get()!=null){
            Intent weatherActivityIntent = new Intent(activityWeakReference.get(), WeatherActivity.class);
            weatherActivityIntent.putExtra(MwConstants.LOCATION_ID,locationId);
            activityWeakReference.get().startActivity(weatherActivityIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   requestLocation();
                }
                break;
        }
    }

    @Override
    public void destroy() {
        mainView = null;
        weatherApi.destroy();
    }


    @Override
    public void onLocationChanged(final Location location) {
        locationManager.removeUpdates(this);
        weatherApi.getLocationsByLatLong(location.getLatitude(), location.getLongitude(), new MetaWeatherApi.OnLocationResultListener() {
           @Override
           public void onLocationResult(List<com.elitemobiletechnology.metaweather.model.Location> locations) {
               mainView.updateLocationList(locations);
           }

           @Override
           public void onError(MetaWeatherApi.GenericWeatherApiError error) {
           }
       });
    }

    private void permissionCheck(){
        if (ContextCompat.checkSelfPermission(WeatherApplication.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activityWeakReference.get(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }else{
            requestLocation();
        }
    }

    private void requestLocation(){
        try {
            locationManager = (LocationManager) WeatherApplication.getAppContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gpsIsEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(networkIsEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }else if(gpsIsEnabled){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
        catch (SecurityException ex) {
            Log.e(TAG, "Location permission did not work!");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

}
