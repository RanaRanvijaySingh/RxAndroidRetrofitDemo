package com.tracer.webservice;

import com.tracer.models.WeatherData;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherApi {
    @GET("weather")
    Observable<Response<WeatherData>> getWeatherData(
            @Query("q") String city,
            @Query("appid") String token);
}
