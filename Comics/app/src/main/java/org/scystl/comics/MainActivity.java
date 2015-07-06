package org.scystl.comics;


import android.app.ListActivity;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scystl.comics.org.scystl.comics.model.ComicCharacter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText searchEditText;
    private ListView listView;
    private ArrayAdapter<String> charactersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));

            isSearchOpened = false;
        } else {
            //open the search entry
            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title
            searchEditText = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            searchEditText.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_search));

            isSearchOpened = true;
        }
    }

    private void doSearch() {
        Log.i("MAN",this.searchEditText.getText().toString());
        if(!searchEditText.getText().toString().isEmpty()) {
            new HttpGetTask().execute();
        }
    }

    private void setAdapter(List<String> result) {
        charactersAdapter = new ArrayAdapter<String>(this, R.layout.comic_template, R.id.comic_description,result);
        listView.setAdapter(charactersAdapter);

    }

    private class HttpGetTask extends AsyncTask<Void, Void, List<String>> {

            AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
            @Override
            protected List<String> doInBackground(Void... params) {
                String name = searchEditText.getText().toString();
                //String gender = "male";
                //String uri = "http://www.comicvine.com/api/characters/?api_key=5058299fc15a6d36a59ab14558aa093ed00cb491&filter=gender:"+gender+"&filter=name:"+name+"&sort=name:%20desc&format=json";
                String uri = "http://www.comicvine.com/api/characters/?api_key=5058299fc15a6d36a59ab14558aa093ed00cb491&filter=name:"+name+"&sort=name:%20desc&limit=10&format=json";
                HttpGet request = new HttpGet(uri);
                JSONResponseHandler responseHandler = new JSONResponseHandler();
                try {
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

                setAdapter(result);
            }
     }

     private class JSONResponseHandler implements ResponseHandler<List<String>> {

            private static final String RESULTS_TAG = "results";
            private static final String NAME_TAG = "name";
            private static final String REAL_NAME_TAG = "real_name";

            @Override
            public List<String> handleResponse(HttpResponse response)
                    throws ClientProtocolException, IOException {
                List<String> result = new ArrayList<String>();
                String JSONResponse = new BasicResponseHandler()
                        .handleResponse(response);
                try {

                    // Get top-level JSON Object - a Map
                    JSONObject responseObject = (JSONObject) new JSONTokener(
                            JSONResponse).nextValue();

                    // Extract value of "earthquakes" key -- a List
                    JSONArray earthquakes = responseObject
                            .getJSONArray(RESULTS_TAG);

                    // Iterate over earthquakes list
                    for (int idx = 0; idx < earthquakes.length(); idx++) {

                        // Get single earthquake data - a Map
                        JSONObject earthquake = (JSONObject) earthquakes.get(idx);
                        String name = earthquake.get(NAME_TAG).toString();
                        // Summarize earthquake data as a string and add it to
                        // result

                        result.add(earthquake.get(NAME_TAG)+" : "+earthquake.get(REAL_NAME_TAG)) ;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }
}