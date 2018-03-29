package com.sielski.marcin.builditbigger.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.sielski.marcin.builditbigger.R;
import com.sielski.marcin.builditbigger.util.BuildItBiggerUtils;

public class BuildItBiggerSettingsFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    public BuildItBiggerSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_builditbigger);
        Preference preference = findPreference(getString(R.string.key_ip_address));
        preference.setSummary(BuildItBiggerUtils.getJokesEndpointIpAddress(getContext()));
        preference.setOnPreferenceChangeListener(this);
        preference = findPreference(getString(R.string.key_port));
        preference.setSummary(BuildItBiggerUtils.getJokesEndpointPort(getContext()));
        preference.setOnPreferenceChangeListener(this);
        //preference.setOnPreferenceClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_ip_address))) {
            findPreference(key).setSummary(sharedPreferences.getString(key,
                    getString(R.string.default_ip_address)));
        }
        if (key.equals(getString(R.string.key_port))) {
            findPreference(key).setSummary(sharedPreferences.getString(key,
                    getString(R.string.default_port)));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        String value = (String)newValue;
        if (key.equals(getString(R.string.key_ip_address))) {
            if (!value.matches(BuildItBiggerUtils.REGEX_IP_ADDRESS)) {
                BuildItBiggerUtils.showSnackBar(getContext(),
                        getActivity().findViewById(R.id.fragment_builditbigger_settings),
                        getString(R.string.snackbar_ip_address));
                return false;
            }
        }
        if (key.equals(getString(R.string.key_port))) {
            int port;
            try {
                port = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
            if (port > 65535) {
                BuildItBiggerUtils.showSnackBar(getContext(),
                        getActivity().findViewById(R.id.fragment_builditbigger_settings),
                        getString(R.string.snackbar_port));
                return false;
            }
        }
        return true;
    }

}