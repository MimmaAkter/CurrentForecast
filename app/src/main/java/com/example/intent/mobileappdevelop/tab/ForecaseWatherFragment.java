package com.example.intent.mobileappdevelop.tab;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intent.mobileappdevelop.tab.WeatherAsync.ForecastPojo.ForecastResponse;
import com.example.intent.mobileappdevelop.tab.WeatherAsync.ForecastPojo.ForecastService;
import com.example.intent.mobileappdevelop.tab.WeatherAsync.WeatherClient;
import com.example.intent.mobileappdevelop.tab.WeatherAsync.WeatherPojo.WeatherData;
import com.example.intent.mobileappdevelop.tab.WeatherAsync.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecaseWatherFragment extends Fragment implements LocationFoundListiner {
    private double latitude;
    private double longitude;
    private boolean isLocationFound;
    private TextView textView;
    private String baseUrl = "https://api.openweathermap.org/data/2.5/";


    public ForecaseWatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecase_wather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.forecastTV);
    }

    @Override
    public void getLocation(double latitude, double longitude) {
        Toast.makeText(getActivity(), "check", Toast.LENGTH_SHORT).show();
        this.latitude = latitude;
        this.longitude = longitude;
        String endurl = String.format("forecast/daily?lat=%f&lon=%f&cnt=%s&appid=%s",
                latitude,longitude,"10",getString(R.string.weatherApiKey));
        ForecastService service = WeatherClient
                .getClient(baseUrl)
                .create(ForecastService.class);
        service.getWeatherData(endurl)
                .enqueue(new Callback<ForecastResponse>() {
                    @Override
                    public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                        if(response.isSuccessful()){
                            ForecastResponse forecast = response.body();
                            String city =  forecast.getCity().getName();
                            String country = forecast.getCity().getCountry();
                            textView.setText("City: "+city+"\n"+"Country: "+country);
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastResponse> call, Throwable t) {
                        //   Toast.makeText(getActivity(), , Toast.LENGTH_LONG).show();
                        textView.setText(t.getLocalizedMessage());
                    }
                });
    }
}
