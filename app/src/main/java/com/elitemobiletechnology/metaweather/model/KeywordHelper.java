package com.elitemobiletechnology.metaweather.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.elitemobiletechnology.metaweather.MwConstants;
import com.elitemobiletechnology.metaweather.R;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class KeywordHelper {
    private Context context;
    public KeywordHelper(Context context){
        this.context = context;
    }
    public Set<String> getKeywords(){
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE);
        Set<String> keywords = prefs.getStringSet(MwConstants.KEYWORD_LIST,null);
        return keywords;
    }

    public void saveKeyword(String keyword){
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE);
        Set<String> keywords = prefs.getStringSet(MwConstants.KEYWORD_LIST,null);
        if(keywords==null){
            keywords = new HashSet<>();
        }
        keywords.add(keyword);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(MwConstants.KEYWORD_LIST);
        editor.apply();
        editor.putStringSet(MwConstants.KEYWORD_LIST,keywords);
        editor.apply();
    }
}
