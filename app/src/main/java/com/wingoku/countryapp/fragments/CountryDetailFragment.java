package com.wingoku.countryapp.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wingoku.countryapp.R;
import com.wingoku.countryapp.adapters.CountryDetailsRecyclerViewAdapter;
import com.wingoku.countryapp.models.CountriesCode;
import com.wingoku.countryapp.models.Country;
import com.wingoku.countryapp.utils.Constants;
import com.wingoku.countryapp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Umer on 3/7/2017.
 */

public class CountryDetailFragment extends Fragment{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.imageView_collapsingToolbar)
    ImageView mCollapsingToolbarImageView;

    @BindView(R.id.list_country_details)
    RecyclerView mDetailsRecyclerView;

    // might get used by Snackbar upon image loading failure
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_country_details, container, false);

        //must bind for View Injection
        ButterKnife.bind(this, mView);

        // getting the country object that contains all the details about the user selected country
        Country country = getArguments().getParcelable(Constants.PARCELABLE_COUNTRY_OBJECT);

        // if there is no data to be shown, then don't execute this fragment further
        if(country == null) {
            getActivity().onBackPressed();
            return mView;
        }

        String countryName = country.getName();

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            setHasOptionsMenu(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mToolbar.setTitle(countryName);

        CountriesCode countriesCode = Utils.getCountriesList(getContext());
        String countryCodeString = countriesCode.getCountryFlagCodes().get(countryName);

        if(countryCodeString != null)
            Picasso.with(getContext()).
                    load(Constants.PATH_TO_HIGH_RES_FLAG+ countryCodeString.toLowerCase()+".png").into(mMyTarget);

        initRecyclerView();
        populateRecyclerViewAdapter(generateDataList(country));

        return mView;
    }

    /**
     * Initialize RecyclerView and assign the LayoutManger
     */
    private void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mDetailsRecyclerView.setLayoutManager(mLayoutManager);
    }

    /**
     * Send required info in the RecyclerView's adapter for displaying data
     */
    private void populateRecyclerViewAdapter(List<HashMap<String, Object>> dataList) {
        CountryDetailsRecyclerViewAdapter adapter = new CountryDetailsRecyclerViewAdapter(getContext(), dataList , R.layout.layout_detail_cell);
        mDetailsRecyclerView.setAdapter(adapter);
    }

    /**
     *
     * @param country Country Object containing all the details about the user selected country
     * @return This method returns a list of specific details about the user selected country. This List will be then assigned to RecyclerView adapter to display info
     */
    private List<HashMap<String, Object>> generateDataList(Country country) {
        String callCode = "";
        String languages = "";
        String currencies = "";

        if(country.getCallingCodes() != null && country.getCallingCodes().size() > 0)
            callCode = country.getCallingCodes().get(0);
        else
            callCode = getString(R.string.not_found);

        if(country.getCurrencies() != null && country.getCurrencies().size() > 0)
            currencies = country.getCurrencies().get(0).get(getString(R.string.key_name));
        else
            currencies = getString(R.string.not_found);

        if(country.getLanguages() != null && country.getLanguages().size() > 0) {
            for(HashMap<String, String> lan : country.getLanguages()) {
                languages+=lan.get(getString(R.string.key_name))+", ";
            }

            // removing the , after the last word
            languages = languages.substring(0, languages.length()-2);
        }
        else
            languages = getString(R.string.not_found);

        // creating list containing specific info about the user selected country
        List<HashMap<String, Object>> countryDataList = new ArrayList<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put(getString(R.string.key_title), getString(R.string.title_capital));
        map.put(getString(R.string.key_name), country.getCapital());
        map.put(getString(R.string.key_resID), R.drawable.ic_capital);
        countryDataList.add(map);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put(getString(R.string.key_title), getString(R.string.title_currency));
        map2.put(getString(R.string.key_name), currencies);
        map2.put(getString(R.string.key_resID), R.drawable.ic_currency);
        countryDataList.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put(getString(R.string.key_title), getString(R.string.title_language));
        map3.put(getString(R.string.key_name), languages);
        map3.put(getString(R.string.key_resID), R.drawable.ic_language);
        countryDataList.add(map3);

        HashMap<String, Object> map4 = new HashMap<>();
        map4.put(getString(R.string.key_title), getString(R.string.title_population));
        map4.put(getString(R.string.key_name), country.getPopulation());
        map4.put(getString(R.string.key_resID), R.drawable.ic_population);
        countryDataList.add(map4);

        HashMap<String, Object> map5 = new HashMap<>();
        map5.put(getString(R.string.key_title), getString(R.string.title_phonecode));
        map5.put(getString(R.string.key_name), callCode);
        map5.put(getString(R.string.key_resID), R.drawable.ic_phone);
        countryDataList.add(map5);

        HashMap<String, Object> map6 = new HashMap<>();
        map6.put(getString(R.string.key_title), getString(R.string.title_continent));
        map6.put(getString(R.string.key_name), country.getRegion());
        map6.put(getString(R.string.key_resID), R.drawable.ic_continent);
        countryDataList.add(map6);

        return countryDataList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // id of the back button in toolbar
            case android.R.id.home:
                getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // creating a strong reference to target object so that it won't get GC before we are able to load the Bitmap in imageView.
    // loading image using Picasso into CollapsingToolbar's imageView changes its scroll behavior. That's why we are using Target object to over come this issue
    private Target mMyTarget = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            mCollapsingToolbarImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Snackbar snackbar = Utils.initSnackbar(getActivity(), mView);
            snackbar.setText(R.string.failed_to_load_image_message);
            snackbar.show();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
}
