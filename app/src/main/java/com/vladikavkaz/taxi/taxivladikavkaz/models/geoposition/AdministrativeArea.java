package com.vladikavkaz.taxi.taxivladikavkaz.models.geoposition;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class AdministrativeArea {

    @SerializedName("AdministrativeAreaName")
    @Expose
    private String administrativeAreaName;
    @SerializedName("Locality")
    @Expose
    private Locality locality;

    public String getAdministrativeAreaName() {
        return administrativeAreaName;
    }

    public void setAdministrativeAreaName(String administrativeAreaName) {
        this.administrativeAreaName = administrativeAreaName;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

}