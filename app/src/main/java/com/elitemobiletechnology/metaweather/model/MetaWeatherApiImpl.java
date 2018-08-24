package com.elitemobiletechnology.metaweather.model;

import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.elitemobiletechnology.metaweather.MwConstants;
import com.elitemobiletechnology.metaweather.model.network.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MetaWeatherApiImpl implements MetaWeatherApi {
    private static final String TAG = "MetaWeatherApiImpl";
    private Gson gson;
    private Handler workerHandler;
    private Handler mainHandler;

    public MetaWeatherApiImpl() {
        gson = new Gson();
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        workerHandler = new Handler(looper);
        mainHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void getLocationsByLatLong(double lattitude, double longitude, @NonNull final OnLocationResultListener resultListener) {
        final String url = MwConstants.META_WEATHER_HOST + "api/location/search/?lattlong=" + lattitude + "," + longitude;
        workerHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpRequest request = HttpRequest.get(url);
                    if (request.ok()) {
                        String body = request.body();
                        try {
                            final Location[] locations = gson.fromJson(body, Location[].class);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    resultListener.onLocationResult(Arrays.asList(locations));
                                }
                            });
                        } catch (JsonSyntaxException ignore) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    resultListener.onError(GenericWeatherApiError.UNKNOWN_ERROR);
                                }
                            });
                        }
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                resultListener.onError(GenericWeatherApiError.UNKNOWN_ERROR);
                            }
                        });
                    }
                } catch (HttpRequest.HttpRequestException ex) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            resultListener.onError(GenericWeatherApiError.NETWORK_ERROR);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getLocationByName(String keyword, @NonNull final OnLocationResultListener resultListener) {
        final String url = MwConstants.META_WEATHER_HOST + "api/location/search/?query=" + Uri.encode(keyword.trim());
        workerHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpRequest request = HttpRequest.get(url);
                    if (request.ok()) {
                        String body = request.body();
                        try {
                            final Location[] locations = gson.fromJson(body, Location[].class);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    resultListener.onLocationResult(Arrays.asList(locations));
                                }
                            });
                        } catch (JsonSyntaxException ignore) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    resultListener.onError(GenericWeatherApiError.UNKNOWN_ERROR);
                                }
                            });
                        }
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                resultListener.onError(GenericWeatherApiError.UNKNOWN_ERROR);
                            }
                        });
                    }
                } catch (HttpRequest.HttpRequestException ex) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            resultListener.onError(GenericWeatherApiError.NETWORK_ERROR);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getFiveDayForcast(String woeid, @NonNull final OnDailyWeatherUpdateListener listener) {
        final String url = MwConstants.META_WEATHER_HOST + "api/location/" + woeid;
        workerHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpRequest request = HttpRequest.get(url);
                    if (request.ok()) {
                        String body = request.body();
                        try {
                            JSONObject json = new JSONObject(body);
                            JSONArray jsonArr = json.getJSONArray("consolidated_weather");
                            if (jsonArr != null) {
                                final DailyWeather[] weatherList = gson.fromJson(jsonArr.toString(), DailyWeather[].class);
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onDailyWeatherUpdate(Arrays.asList(weatherList));
                                    }
                                });
                            } else {
                                throw new Exception();
                            }
                        } catch (Exception ignore) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onError(GenericWeatherApiError.UNKNOWN_ERROR);
                                }
                            });
                        }
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(GenericWeatherApiError.UNKNOWN_ERROR);
                            }
                        });
                    }
                } catch (HttpRequest.HttpRequestException ex) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(GenericWeatherApiError.NETWORK_ERROR);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void destroy() {
        workerHandler.removeCallbacksAndMessages(null);
        mainHandler.removeCallbacksAndMessages(null);
    }
}
