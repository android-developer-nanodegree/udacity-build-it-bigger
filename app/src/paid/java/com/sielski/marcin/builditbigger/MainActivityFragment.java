package com.sielski.marcin.builditbigger;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                    new JokesEndpointAsyncTask(activity, R.id.progress_bar).execute();
                }
            }
        });

        return root;
    }

}

