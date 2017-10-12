package com.vladikavkaz.taxi.taxivladikavkaz.models.geoposition;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("formatted")
    @Expose
    private String formatted;
    @SerializedName("Components")
    @Expose
    private List<Component> components = null;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}