package org.scystl.comics;

import android.app.ListActivity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scystl.comics.service.md5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NetworkingAndroidHTTPClientJson extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.list_item);
        new HttpGetTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_networking_android_httpclient_json, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class HttpGetTask extends AsyncTask<Void, Void, List<String>> {

        AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
        @Override
        protected List<String> doInBackground(Void... params) {
            //Datos de la cuenta de Marvel Developer:
            String privateKey = "9536cc88cbadcb50b11ef4a1aed89447e9d12574";
            String apiKey = "5c88e5e19963af1a232d69dc7ac9dd51";
            String timestamp = "cualquierCadena";
            final String BASE_URL = "http://gateway.marvel.com:80/v1/public/characters?name=tony&";
            final String TS_PARAM = "ts";
            final String API_KEY_PARAM = "apikey";
            final String HASH_PARAM = "hash";
            String hash = md5.md5(timestamp + privateKey + apiKey);

            final String urlComic = BASE_URL+"&ts="+timestamp+"&apikey="+apiKey+"&hash="+hash;
            HttpGet request = new HttpGet(urlComic);
            JSONResponseHandler responseHandler = new JSONResponseHandler();
            try {
                //ERROR AL OBTENER EL RECURSO :
                return mClient.execute(request, responseHandler);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (null != mClient)
                mClient.close();

            //setListAdapter(new ArrayAdapter<String>(NetworkingAndroidHTTPClientJson.this,R.layout.list_item, result));
            Log.i("GETING:","SE OBTUVO"+result.size());
        }
    }

    private class JSONResponseHandler implements ResponseHandler<List<String>> {


        private static final String RESULTS_TAG = "results";
        private static final String ALIASES_TAG = "aliases";
        private static final String NAME_TAG = "name";

        @Override
        public List<String> handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException {
            //---Codigo en construccion, se implementara despues de fixear el error del url
            /*
            List<String> result = new ArrayList<String>();
            String JSONResponse = new BasicResponseHandler()
                    .handleResponse(response);
            try {

                // Get top-level JSON Object - a Map
                JSONObject responseObject = (JSONObject) new JSONTokener(
                        JSONResponse).nextValue();

                // Extract value of "earthquakes" key -- a List
                //JSONArray earthquakes = responseObject
                //		.getJSONArray(EARTHQUAKE_TAG);
                JSONArray earthquakes = responseObject
                        .getJSONArray(RESULTS_TAG);


                // Iterate over earthquakes list
                for (int idx = 0; idx < earthquakes.length(); idx++) {

                    // Get single earthquake data - a Map
                    JSONObject earthquake = (JSONObject) earthquakes.get(idx);

                    // Summarize earthquake data as a string and add it to
                    // result

                    result.add(MAGNITUDE_TAG + ":"
							+ earthquake.get(MAGNITUDE_TAG) + ","
							+ LATITUDE_TAG + ":"
							+ earthquake.getString(LATITUDE_TAG) + ","
							+ LONGITUDE_TAG + ":"
							+ earthquake.get(LONGITUDE_TAG));

                    result.add("Nombre :"
                            + earthquake.get(NAME_TAG) + ","
                            +  "Aliases:"
                            + earthquake.getString(ALIASES_TAG));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            */
            return null;
        }
    }
}
