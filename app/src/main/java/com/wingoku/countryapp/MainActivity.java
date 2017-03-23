package com.wingoku.countryapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.wingoku.countryapp.fragments.CountryDetailFragment;
import com.wingoku.countryapp.fragments.CountryListFragment;
import com.wingoku.countryapp.interfaces.RestCountriesAPI;
import com.wingoku.countryapp.models.Country;
import com.wingoku.countryapp.utils.Constants;
import com.wingoku.countryapp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // this idling resource will be used by Espresso to wait for and synchronize with RetroFit Network call
    // https://youtu.be/uCtzH0Rz5XU?t=3m23s
    CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");
    private FragmentManager mFManager;

    @BindView(R.id.fragment_container)
    LinearLayout mFragmentContainer;

    private String mUserSelectedCountryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initializing Crashlytics for error reporting
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        //must bind for View Injection
        ButterKnife.bind(this);
        mFManager = getSupportFragmentManager();
        openFragment(new CountryListFragment(), true, false, false, false);
    }

    /**
     * Open a fragment
     *
     * @param frag Fragment to open
     * @param isReplaced should this fragment replace current visible fragment
     * @param isAdded should this fragment be added on top of current fragment
     * @param addToBackStack should this fragment be added to backstack for removal upon onBackPressed
     * @param setEnterAnimation should this fragment be animated when replaced/added in the fragment container
     *
     *
     */
    private void openFragment(Fragment frag, boolean isReplaced, boolean isAdded, boolean addToBackStack, boolean setEnterAnimation) {
        FragmentTransaction fTranscation = mFManager.beginTransaction();

        if(setEnterAnimation)
            fTranscation.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        else
            fTranscation.setCustomAnimations(0, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

        if (isReplaced)
            fTranscation.replace(R.id.fragment_container, frag);
        else if (isAdded)
            fTranscation.add(R.id.fragment_container, frag);

        if (addToBackStack)
            fTranscation.addToBackStack(frag.getClass().getSimpleName());

        fTranscation.commit();
    }

    /**
     * This method will fetch details for a country based on the country's name from Restful Country server on background thread
     *
     * @param countryName Name of the country for which the information is to be fetched from server
     *                    This method fetches country data on background thread. Country Detail fragment will
     *                    open automatically once the data has been fetched.
     *
     *                    In case of failure, a snackbar message will be displayed for user's convenience.
     */
    private void fetchCountryInfoByName(String countryName) {
        // increment idling resource for telling Espresso wait for the RetroFit network's call
        espressoTestIdlingResource.increment();
        final Snackbar snackbar = Utils.initSnackbar(this, mFragmentContainer);

        RestCountriesAPI.Factory.getInstance().getCountryByName(countryName).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                Utils.dismissWaitDialog();

                if(response.body() == null || response.body().get(0) == null) {
                    onFailureInDataFetching(snackbar);
                    return;
                }

                CountryDetailFragment countryDetailFragment = setCountryDetailFragmentBundle(response.body().get(0));
                openFragment(countryDetailFragment, true, false, true, true);
                snackbar.setText(getString(R.string.success_message)).show();

                // decrement idling resource to tell Espresso that the Retrofit Network call has been completed
                espressoTestIdlingResource.decrement();
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Utils.dismissWaitDialog();
                onFailureInDataFetching(snackbar);

                Crashlytics.log(Log.ERROR, getClass().getSimpleName() ,t.getLocalizedMessage());
                Crashlytics.logException(t);
            }
        });
    }

    /**
     * Set the country object that will be created into a parcelable in order to be sent as an argument to {@link CountryDetailFragment}
     *
     * @param country this object contains all the information about the country that the user tapped on.
     *                This method will open {@link CountryDetailFragment} to display further details about the user selected country
     *
     * @return Returns {@link CountryDetailFragment} object with {@link Country} set in arguments
     */
    private CountryDetailFragment setCountryDetailFragmentBundle(Country country) {
        // ensuring the Name of the country in /assets/cuontriesFlag.json file is the one in the COuntry object too. If the name
        // of the country sent from the RestCountries server is different then the name in countriesFlag.json file,
        // the application won't be able to get the countryCode from the countriesFlag.json file and hence the app won't be able to show
        // flag in CountryDetailFragment.
        country.setName(mUserSelectedCountryName);
        Bundle bund = new Bundle();
        bund.putParcelable(Constants.PARCELABLE_COUNTRY_OBJECT, country);

        CountryDetailFragment countryDetailFragment = new CountryDetailFragment();
        countryDetailFragment.setArguments(bund);

        return countryDetailFragment;
    }

    private void onFailureInDataFetching(Snackbar snackbar) {
        snackbar.setText(getString(R.string.failure_message)).show();

        // decrement idling resource to tell Espresso that the Retrofit Network call has been completed
        espressoTestIdlingResource.decrement();
    }

    /**
     * Set the name of the country that user clicked on. This method will in turn call {@link MainActivity#fetchCountryInfoByName(String)}
     * to fetch the details of the country
     *
     * @param countryName name of the country on which the user has tapped.
     *                    This method is called from the {@link CountryListFragment} when the user touches on a card.
     *                    This method will call {@link #fetchCountryInfoByName(String)} to fetch further info from server
     */
    public void clickedCountryInfo(String countryName) {
        mUserSelectedCountryName = countryName;
        Utils.showWaitDialog(this, getString(R.string.please_wait), getString(R.string.fetching_data_message));
        fetchCountryInfoByName(countryName);
    }

    /**
     * This method will return Espresso IdlingResource for aiding sync between RetroFit's custom background threads & Espresso
     *
     * @return MainActvity's idling resource for Espresso testing
     */
    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return espressoTestIdlingResource;
    }
}
