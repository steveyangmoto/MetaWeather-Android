package com.elitemobiletechnology.metaweather;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.elitemobiletechnology.metaweather.model.Location;
import com.elitemobiletechnology.metaweather.presenter.MainPresenter;
import com.elitemobiletechnology.metaweather.presenter.MainPresenterImpl;
import com.elitemobiletechnology.metaweather.view.LocationListAdapter;
import com.elitemobiletechnology.metaweather.view.MainView;

/**
 * Steve Yang
 * Note: search history is display as auto complete suggestions.
 * For example, supposed San Francisco and San Jose were in search history.
 * Then they will show in the drop down list when user enters "Sa"
 */

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView,AdapterView.OnItemClickListener,View.OnClickListener{
    private MainPresenter presenter;
    private ListView listView;
    private Button searchButton;
    private AutoCompleteTextView tvInput;
    private LocationListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.locationList);
        tvInput = (AutoCompleteTextView) findViewById(R.id.tvInput);
        searchButton = (Button) findViewById(R.id.btSearch);
        searchButton.setOnClickListener(this);
        presenter = new MainPresenterImpl(this,this);
        presenter.onActivityCreate();
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(R.id.empty_list_view));
    }

    @Override
    public void onClick(View v) {//on search button click
        presenter.onSearchButtonClicked(tvInput.getText().toString());
    }

    @Override//on location list item click
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.onLocationListItemClicked(parent,position);
    }

    @Override
    public void updateLocationList(List<Location> locations) {
        adapter = new LocationListAdapter(this.getApplicationContext(), locations);
        listView.setAdapter(adapter);
    }

    @Override
    public void updateSearchHistory(List<String> keywords) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, keywords);
        tvInput.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        presenter.destroy();
    }
}
