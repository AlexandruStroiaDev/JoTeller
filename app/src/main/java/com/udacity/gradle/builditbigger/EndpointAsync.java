package com.udacity.gradle.builditbigger;

        import android.os.AsyncTask;

        import com.google.api.client.extensions.android.http.AndroidHttp;
        import com.google.api.client.extensions.android.json.AndroidJsonFactory;
        import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
        import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
        import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

        import java.io.IOException;

public class EndpointAsync extends AsyncTask<Void, Void, String> {
    private MyApi myApiService = null;

    @Override
    protected String doInBackground(Void... voids) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(BuildConfig.URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            myApiService = builder.build();
        }

        try {
            return myApiService.sayJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
