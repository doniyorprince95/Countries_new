package com.wingoku.countryapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wingoku.countryapp.R;
import com.wingoku.countryapp.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Umer on 3/6/2017.
 */

public class CountriesListRecyclerViewAdapter extends RecyclerView.Adapter<CountriesListRecyclerViewAdapter.CountriesListRecyclerViewHolder>{

    private Context mContext;
    private int mLayoutFileID;
    private HashMap<String, String> mCountriesMap;

    private List<String> mCountriesNameList;

    /**
     *
     * @param context Application/Activity Context
     * @param countriesNameList List of all the countries in /assets/countriesFlag.json
     * @param countriesCodeMap key value pairs of Full Country Names & Country Codes
     * @param cellLayoutFileID Resource ID of the layout that will be used by the recyclerView
     */
    public CountriesListRecyclerViewAdapter(Context context, List<String> countriesNameList, HashMap<String, String> countriesCodeMap, int cellLayoutFileID){
        mContext = context;
        mCountriesNameList = countriesNameList;
        mCountriesMap = countriesCodeMap;
        mLayoutFileID = cellLayoutFileID;
    }

    @Override
    public CountriesListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(mLayoutFileID, parent, false);

        return new CountriesListRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountriesListRecyclerViewHolder holder, int position) {
        String countryName = mCountriesNameList.get(position);
        holder.mCountryNameTextView.setText(countryName);
        holder.mCountryCodeTextView.setText(mCountriesMap.get(countryName));

        String countryCode = mCountriesMap.get(countryName);

        // loading image from the assets/flags folder using Picasso to ensure smooth UI and non UI blocking
        Picasso.with(mContext).load(Constants.PATH_TO_HIGH_RES_FLAG+countryCode.toLowerCase()+".png").into(holder.mCountryFlagImageView);
    }

    @Override
    public int getItemCount() {
        return mCountriesNameList.size();
    }

    static class CountriesListRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.imageView_countryFlag)
        ImageView mCountryFlagImageView;

        @BindView(R.id.tv_countryName)
        TextView mCountryNameTextView;

        @BindView(R.id.tv_countryCode)
        TextView mCountryCodeTextView;

        @BindView(R.id.card_view)
        CardView mCardView;

        public CountriesListRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mCardView.setOnClickListener(this);
            // in case the user taps on image, we should be able to recieve that click event
            mCountryFlagImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /**
             * The event bus will relay this event to {@link com.wingoku.countryapp.fragments.CountryListFragment} that will in turn send this to MainActivity
             */
            EventBus.getDefault().post(mCountryNameTextView.getText().toString());
        }
    }
}