package com.elitemobiletechnology.metaweather.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elitemobiletechnology.metaweather.R;
import com.elitemobiletechnology.metaweather.model.Location;
import com.elitemobiletechnology.metaweather.presenter.LocationListPresenter;
import com.elitemobiletechnology.metaweather.presenter.LocationListPresenterImpl;

import java.util.List;

public class LocationListAdapter extends BaseAdapter{
    LayoutInflater inflater;
    LocationListPresenter presenter;

    public LocationListAdapter(Context context,List<Location> locationList){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        presenter = new LocationListPresenterImpl(locationList);
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
        LocationListAdapter.ViewHolder holder;
        if(view==null){
            view = inflater.inflate(R.layout.location_list_row,parent,false);
            holder = new LocationListAdapter.ViewHolder((ViewGroup)view);
            view.setTag(holder);
        }else{
            holder = (LocationListAdapter.ViewHolder)view.getTag();
        }
        presenter.onBindViewHolder(holder,position);
        return view;
    }

    public static class ViewHolder{
        TextView tvId;
        TextView tvTitle;
        TextView tvType;
        public ViewHolder(ViewGroup vg){
            tvId = (TextView)vg.findViewById(R.id.tvId);
            tvTitle = (TextView)vg.findViewById(R.id.tvTitle);
            tvType = (TextView)vg.findViewById(R.id.tvType);
        }

        public void setId(String id){
            this.tvId.setText(id);
        }

        public void setTitle(String title){
            this.tvTitle.setText(title);
        }

        public void setType(String type){
            this.tvType.setText(type);
        }
    }
}
