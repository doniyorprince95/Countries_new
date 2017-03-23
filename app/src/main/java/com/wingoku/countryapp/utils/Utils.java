package com.wingoku.countryapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wingoku.countryapp.R;
import com.wingoku.countryapp.models.CountriesCode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by Umer on 3/6/2017.
 */

public class Utils {
    private static ProgressDialog mProgressDialog;
    private static CountriesCode mCountriesCode = null;

    /**
     *
     * @param con Activity/Application context
     * @return returns @{@link CountriesCode} object that contains the KeyValue pairs of CountryFullName:CountryCode
     */
    public static CountriesCode getCountriesList(Context con) {
        if(mCountriesCode != null)
            return mCountriesCode;

        Gson gson = new Gson();
        Type type = new TypeToken<CountriesCode>(){}.getType();
        mCountriesCode = gson.fromJson(loadJSONFromAssets(con, Constants.COUNTRY_FLAG_JSON_FILE_NAME), type);
        return mCountriesCode;
    }

    /**
     *
     * @param con Activity/Application context
     * @param fileName name of the files to be read
     * @return returns string containing JSON that's read from assets/countriesFlag.json file
     */
    private static String loadJSONFromAssets(Context con, String fileName) {
        String json = null;
        try {
            InputStream is = con.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Crashlytics.log(Log.ERROR, con.getClass().getSimpleName() ,ex.getLocalizedMessage());
            Crashlytics.logException(ex);
            return null;
        }
        return json;
    }

    /**
     *
     * @param con Activity/Application context
     * @param title Title to be displayed in Progress Dialog Title area
     * @param message Message to be display in Progress Dialog
     */
    public static void showWaitDialog(Context con, String title, String message) {
        mProgressDialog = new ProgressDialog(con);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    /**
     * Dismisses the ProgressDialog that's being display on the screen
     */
    public static void dismissWaitDialog() {
        if(mProgressDialog == null)
            return;

        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    /**
     *
     * @param con Activity/Application context
     * @param view View with which {@link Snackbar} will be attached
     * @return Useable Snackbar object
     */
    public static Snackbar initSnackbar(Context con, View view) {
        final Snackbar snackBar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        snackBar.setAction(con.getString(R.string.ok_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.setActionTextColor(Color.GREEN);

        return snackBar;
    }
}
