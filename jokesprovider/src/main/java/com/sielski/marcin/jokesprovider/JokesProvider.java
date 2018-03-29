package com.sielski.marcin.jokesprovider;

import com.google.gson.Gson;
import com.sielski.marcin.jokesprovider.data.Response;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class JokesProvider {
    private static final String URL = "http://api.icndb.com/jokes/random";

    public static String getJoke() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(URL).build();
            okhttp3.Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody == null) return null;
            return (new Gson().fromJson(responseBody.string(), Response.class)).value.joke;
        } catch (IOException e) {
            return null;
        }
    }

}
