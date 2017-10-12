package com.vladikavkaz.taxi.taxivladikavkaz.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vladikavkaz.taxi.taxivladikavkaz.models.geoposition.Response;

public class GeoLocationModel {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}