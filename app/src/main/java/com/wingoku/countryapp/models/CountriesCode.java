package com.wingoku.countryapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

/**
 * Created by Umer on 3/6/2017.
 */

/**
 *  Contains a keyValue pair of CountryName:CountryCode in {@link #countryFlagCodes}
 */
public class CountriesCode {
    @SerializedName("flagCodes")
    @Expose
    private LinkedHashMap<String, String> countryFlagCodes;

    public LinkedHashMap<String, String> getCountryFlagCodes() {
        return countryFlagCodes;
    }

    public void setCountryFlagCodes(LinkedHashMap<String, String> countryFlagCodes) {
        this.countryFlagCodes = countryFlagCodes;
    }
}
