package com.sielski.marcin.builditbigger;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sielski.marcin.builditbigger.util.BuildItBiggerUtils;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        root.findViewById(R.id.button_telljoke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Activity activity = getActivity();
                if(activity != null && BuildItBiggerUtils.checkNetworkAvailability(activity)) {
                    final InterstitialAd interstitialAd = new InterstitialAd(activity);
                    interstitialAd.setAdUnitId(BuildItBiggerUtils.INTERSTITIAL_AD_FORMAT);
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            interstitialAd.show();
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            new JokesEndpointAsyncTask(activity, R.id.progress_bar).execute();
                        }
                    });
                }
            }
        });

        AdView mAdView = root.findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        MobileAds.initialize(getContext(), BuildItBiggerUtils.ADMOB_APP_ID);
        return root;
    }

}
