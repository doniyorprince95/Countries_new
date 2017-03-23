package com.wingoku.countryapp.models;

/**
 * Created by Umer on 3/6/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A parcelable model class that contains all the info about a country provided by www.restCountries.eu
 */
public class Country implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("topLevelDomain")
    @Expose
    private List<String> topLevelDomain = null;
    @SerializedName("alpha2Code")
    @Expose
    private String alpha2Code;
    @SerializedName("alpha3Code")
    @Expose
    private String alpha3Code;
    @SerializedName("callingCodes")
    @Expose
    private List<String> callingCodes = null;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("altSpellings")
    @Expose
    private List<String> altSpellings = null;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("subregion")
    @Expose
    private String subregion;

    @SerializedName("population")
    @Expose
    private int population;
    @SerializedName("latlng")
    @Expose
    private List<Double> latlng = null;
    @SerializedName("demonym")
    @Expose
    private String demonym;
    @SerializedName("area")
    @Expose
    private double area;
    @SerializedName("gini")
    @Expose
    private double gini;
    @SerializedName("timezones")
    @Expose
    private List<String> timezones = null;
    @SerializedName("borders")
    @Expose
    private List<String> borders = null;
    @SerializedName("nativeName")
    @Expose
    private String nativeName;
    @SerializedName("numericCode")
    @Expose
    private String numericCode;
    @SerializedName("currencies")
    @Expose
    private List<HashMap<String, String>> currencies = null;

    @SerializedName("translations")
    @Expose
    private HashMap<String, String> translations = null;

    @SerializedName("languages")
    @Expose
    private List<HashMap<String, String>> languages = null;

    public List<HashMap<String, String>> getLanguages() {
        return languages;
    }

    public void setLanguages(List<HashMap<String, String>> languages) {
        this.languages = languages;
    }

    public HashMap<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(HashMap<String, String> translations) {
        this.translations = translations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTopLevelDomain() {
        return topLevelDomain;
    }

    public void setTopLevelDomain(List<String> topLevelDomain) {
        this.topLevelDomain = topLevelDomain;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(List<String> callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(List<String> altSpellings) {
        this.altSpellings = altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Double> latlng) {
        this.latlng = latlng;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getGini() {
        return gini;
    }

    public void setGini(double gini) {
        this.gini = gini;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public List<HashMap<String, String>> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<HashMap<String, String>> currencies) {
        this.currencies = currencies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringList(this.topLevelDomain);
        dest.writeString(this.alpha2Code);
        dest.writeString(this.alpha3Code);
        dest.writeStringList(this.callingCodes);
        dest.writeString(this.capital);
        dest.writeStringList(this.altSpellings);
        dest.writeString(this.region);
        dest.writeString(this.subregion);
        dest.writeInt(this.population);
        dest.writeList(this.latlng);
        dest.writeString(this.demonym);
        dest.writeDouble(this.area);
        dest.writeDouble(this.gini);
        dest.writeStringList(this.timezones);
        dest.writeStringList(this.borders);
        dest.writeString(this.nativeName);
        dest.writeString(this.numericCode);
        dest.writeList(this.currencies);
        dest.writeSerializable(this.translations);
        dest.writeList(this.languages);
    }

    public Country() {
    }

    protected Country(Parcel in) {
        this.name = in.readString();
        this.topLevelDomain = in.createStringArrayList();
        this.alpha2Code = in.readString();
        this.alpha3Code = in.readString();
        this.callingCodes = in.createStringArrayList();
        this.capital = in.readString();
        this.altSpellings = in.createStringArrayList();
        this.region = in.readString();
        this.subregion = in.readString();
        this.population = in.readInt();
        this.latlng = new ArrayList<Double>();
        in.readList(this.latlng, Double.class.getClassLoader());
        this.demonym = in.readString();
        this.area = in.readDouble();
        this.gini = in.readDouble();
        this.timezones = in.createStringArrayList();
        this.borders = in.createStringArrayList();
        this.nativeName = in.readString();
        this.numericCode = in.readString();
        this.currencies = new ArrayList<HashMap<String, String>>();
        in.readList(this.currencies, List.class.getClassLoader());
        this.translations = (HashMap<String, String>) in.readSerializable();
        this.languages = new ArrayList<HashMap<String, String>>();
        in.readList(this.languages, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
