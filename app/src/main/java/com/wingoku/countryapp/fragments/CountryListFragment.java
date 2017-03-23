package com.wingoku.countryapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wingoku.countryapp.MainActivity;
import com.wingoku.countryapp.R;
import com.wingoku.countryapp.adapters.CountriesListRecyclerViewAdapter;
import com.wingoku.countryapp.models.CountriesCode;
import com.wingoku.countryapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Umer on 3/6/2017.
 */

public class CountryListFragment extends Fragment {

    @BindView(R.id.list_countries)
    RecyclerView mCountriesListRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries_list, container, false);

        //must bind for View Injection
        ButterKnife.bind(this, view);

        // register event bus to receive events
        EventBus.getDefault().register(this);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        initRecyclerView();
        populateRecyclerViewAdapter();
        return view;
    }

    /**
     * Initialize RecyclerView and assign the LayoutManger
     */
    private void initRecyclerView() {
        // show two columns in the RecyclerView grid
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mCountriesListRecyclerView.setLayoutManager(mLayoutManager);
    }

    /**
     * Send required info in the RecyclerView's adapter for displaying data
     */
    private void populateRecyclerViewAdapter() {
        CountriesCode codes = Utils.getCountriesList(getContext());
        List<String> countriesNameList = new ArrayList<>(codes.getCountryFlagCodes().keySet());
        CountriesListRecyclerViewAdapter adapter = new CountriesListRecyclerViewAdapter(getContext(), countriesNameList,
                codes.getCountryFlagCodes(), R.layout.layout_country_card);
        mCountriesListRecyclerView.setAdapter(adapter);
    }

    /**
     * Call this when RecyclerView cells/card is clicked
     *
     * @param countryName name of the country upon the user tapped. This method is called by EventBus that delegated the click event from {@link CountriesListRecyclerViewAdapter}
     *                    This event will be further relayed to {@link com.wingoku.countryapp.MainActivity#clickedCountryInfo(String)}
     */
    @Subscribe
    public void onCountryListCellClicked(String countryName) {
        ((MainActivity)getActivity()).clickedCountryInfo(countryName);
    }

    @Override
    public void onDestroyView() {
        // unregister event bus
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
