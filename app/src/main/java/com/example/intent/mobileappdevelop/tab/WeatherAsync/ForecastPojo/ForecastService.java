package com.example.intent.mobileappdevelop.tab.WeatherAsync.ForecastPojo;

import com.example.intent.mobileappdevelop.tab.WeatherAsync.WeatherPojo.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ForecastService {
    @GET
    Call<ForecastResponse> getWeatherData(@Url String endurl);
}
