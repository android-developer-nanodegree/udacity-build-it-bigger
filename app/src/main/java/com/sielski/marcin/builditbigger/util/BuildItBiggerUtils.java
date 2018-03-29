package com.sielski.marcin.builditbigger.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.sielski.marcin.builditbigger.R;

public final class BuildItBiggerUtils {

    public static final String REGEX_IP_ADDRESS =
            "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    public static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

    public static final String INTERSTITIAL_AD_FORMAT = "ca-app-pub-3940256099942544/1033173712";

    private final static class URL {
        final static String SCHEME = "http";
        final static String _AH = "_ah";
        final static String API = "api";
    }

    public static void showSnackBar(Context context, View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        ((TextView) view.findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        snackbar.show();
    }

    public static String getJokesEndpointIpAddress(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(context.getString(R.string.key_ip_address),
                context.getString(R.string.default_ip_address));
    }

    public static String getJokesEndpointPort(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(context.getString(R.string.key_port),
                context.getString(R.string.default_port));
    }

    public static String getJokesEndpointUrl(Context context) {
        return Uri.parse(String.format("%s://%s:%s", URL.SCHEME,
                getJokesEndpointIpAddress(context), getJokesEndpointPort(context))).buildUpon()
                .appendPath(URL._AH).appendPath(URL.API).build().toString();
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static boolean checkNetworkAvailability(Activity activity) {
        if (!BuildItBiggerUtils.isNetworkAvailable(activity)) {
            BuildItBiggerUtils.showSnackBar(activity,
                    activity.findViewById(R.id.button_telljoke),
                    activity.getString(R.string.snackbar_network_unavailable));
            return false;
        }
        return true;
    }
}
