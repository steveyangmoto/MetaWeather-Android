package com.elitemobiletechnology.metaweather.presenter;

import android.widget.AdapterView;

public interface MainPresenter {
    void onActivityCreate();
    void onSearchButtonClicked(String keyword);
    void onLocationListItemClicked(AdapterView adapterView,int position);
    void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults);
    void destroy();
}
