package com.wingoku.countryapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wingoku.countryapp.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Umer on 3/6/2017.
 */

public class CountryDetailsRecyclerViewAdapter extends RecyclerView.Adapter<CountryDetailsRecyclerViewAdapter.CountryDetailRecyclerViewHolder>{

    private Context mContext;
    private int mLayoutFileID;
    private List<HashMap<String, Object>> mCountryDetailDataList;

    /**
     *
     * @param context context Application/Activity Context
     * @param data This object contains specific details about the user selected country
     * @param cellLayoutFileID Resource ID of the layout that will be used by the recyclerView
     */
    public CountryDetailsRecyclerViewAdapter(Context context, List<HashMap<String, Object>> data, int cellLayoutFileID){
        mContext = context;
        mCountryDetailDataList = data;
        mLayoutFileID = cellLayoutFileID;
    }

    @Override
    public CountryDetailRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(mLayoutFileID, parent, false);

        return new CountryDetailRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryDetailRecyclerViewHolder holder, int position) {
        HashMap<String, Object> map = mCountryDetailDataList.get(position);
        int imageResID = (Integer)map.get(mContext.getString(R.string.key_resID));
        String title = map.get(mContext.getString(R.string.key_title)).toString();
        String name = map.get(mContext.getString(R.string.key_name)).toString();

        holder.mTitle.setText(title);
        holder.mName.setText(name);
        Picasso.with(mContext).load(imageResID).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mCountryDetailDataList.size();
    }

    static class CountryDetailRecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imageview_icon)
        ImageView mImageView;

        @BindView(R.id.tv_title)
        TextView mTitle;

        @BindView(R.id.tv_name)
        TextView mName;

        CountryDetailRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
