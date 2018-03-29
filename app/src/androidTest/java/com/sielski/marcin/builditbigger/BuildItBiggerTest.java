package com.sielski.marcin.builditbigger;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class BuildItBiggerTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void asyncTask() {
        Activity activity = mActivityTestRule.getActivity();
        JokesEndpointAsyncTask jokesEndpointAsyncTask =
                new JokesEndpointAsyncTask(activity);
        jokesEndpointAsyncTask.execute();
        String result = null;
        try {
             result = jokesEndpointAsyncTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(result);
    }
}
