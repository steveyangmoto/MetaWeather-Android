package com.elitemobiletechnology.metaweather.presenter;

import com.elitemobiletechnology.metaweather.view.LocationListAdapter;

public interface LocationListPresenter {
    int getCount();

    Object getItem(int position);

    long getItemId(int position);

    void onBindViewHolder(LocationListAdapter.ViewHolder holder,int position);

}
