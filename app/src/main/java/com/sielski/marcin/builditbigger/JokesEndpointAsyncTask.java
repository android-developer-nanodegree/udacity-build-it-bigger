package com.sielski.marcin.builditbigger;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.sielski.marcin.builditbigger.backend.jokesApi.JokesApi;
import com.sielski.marcin.builditbigger.util.BuildItBiggerUtils;
import com.sielski.marcin.jokesactivity.JokesActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;

class JokesEndpointAsyncTask extends AsyncTask<Void, Void, String> {
    private JokesApi jokesApiService = null;
    private final WeakReference<Activity> mActivityWeakReference;

    JokesEndpointAsyncTask(Activity activity, int id) {
        View view = null;
        if (activity != null && id != 0) view = activity.findViewById(id);
        if (view != null) view.setVisibility(View.VISIBLE);
        mActivityWeakReference = new WeakReference<>(activity);
    }

    JokesEndpointAsyncTask(Activity activity) {
        this(activity, 0);
    }

    @Override
    protected String doInBackground(Void... params) {
        Activity activity = mActivityWeakReference.get();
        if (activity == null || activity.isFinishing()) return null;

        if(jokesApiService == null) {
            JokesApi.Builder builder = new JokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(BuildItBiggerUtils.getJokesEndpointUrl(activity))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(
                                AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            jokesApiService = builder.build();
        }

        try {
            return jokesApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Activity activity = mActivityWeakReference.get();
        if (activity == null || activity.isFinishing()) return;
        View view = activity.findViewById(R.id.progress_bar);
        if (view != null) {
            view.setVisibility(View.GONE);
            if (result == null) {
                BuildItBiggerUtils.showSnackBar(activity, view,
                        String.format("%s %s.",
                                activity.getString(R.string.snackbar_connection_failed),
                                BuildItBiggerUtils.getJokesEndpointUrl(activity)));
                return;
            }
        }
        Intent intent = new Intent(activity, JokesActivity.class);
        intent.putExtra(JokesActivity.EXTRA, result);
        activity.startActivity(intent);
    }
}
