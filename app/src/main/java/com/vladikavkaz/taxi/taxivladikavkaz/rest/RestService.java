package com.vladikavkaz.taxi.taxivladikavkaz.rest;


import com.vladikavkaz.taxi.taxivladikavkaz.models.GeoLocationModel;

import java.util.List;

import rx.Observable;

public final class RestService {
    private RestClient restClient;
    public RestService(){
        restClient = new RestClient();
    }

    public Observable<GeoLocationModel> getGeoInfo(String geoCode, String format, String results){
        return restClient.getYandexApi().getGeoLocationInfo(geoCode, format, results);
    }
}
