package com.vladikavkaz.taxi.taxivladikavkaz.rest;

import com.vladikavkaz.taxi.taxivladikavkaz.models.GeoLocationModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface YandexApi {
    @GET("/1.x/")
    Observable<GeoLocationModel> getGeoLocationInfo(@Query("geocode") String geocode,
                                                         @Query("format") String format,
                                                         @Query("results") String results);
}
