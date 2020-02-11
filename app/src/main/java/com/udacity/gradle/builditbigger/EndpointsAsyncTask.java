package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<EndpointsAsyncTask.EndpointsListener, Void, String> {
    private final String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();
    private static MyApi myApiService = null;
    private EndpointsListener endpointsListener;

    @Override
    protected String doInBackground(EndpointsListener... listeners) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }
        endpointsListener = listeners[0];

        try {
            return myApiService.joke().execute().getData();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        endpointsListener.onPostExecute(result);
    }

    public interface EndpointsListener {
        void onPostExecute(String result);
    }
}