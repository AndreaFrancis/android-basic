package org.scystl.comics;


import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public String MAIN_ACTIVITY_TAG = "MainActivity";
    private Toolbar mainToolbar;
    private MenuItem searchAction;
    private boolean isSearchOptionActive = false;
    private EditText searchEditText;
    private ListView charactersListView;
    private CharactersAdapter charactersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        charactersListView = (ListView) findViewById(R.id.listView);
        charactersListView.setOnItemClickListener(this);
        mainToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mainToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchAction = menu.findItem(R.id.action_search);
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
        ActionBar action = getSupportActionBar();
        if(isSearchOptionActive){

            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

            //add the search icon in the action bar
            searchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));
            isSearchOptionActive = false;
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
            searchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_search));

            isSearchOptionActive = true;
        }
    }

    private void doSearch() {
        Log.i(MAIN_ACTIVITY_TAG,this.searchEditText.getText().toString());
        if(!searchEditText.getText().toString().isEmpty()) {
            new HttpGetTask().execute();
        }
    }

    private void setAdapter(List<ComicCharacter> result) {
        charactersAdapter = new CharactersAdapter(result, this);
        charactersListView.setAdapter(charactersAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ComicCharacter characterSelected = this.charactersAdapter.getItem(position);
        Intent characterDetailIntent = new Intent(this, CharacterDetailActivity.class);
        characterDetailIntent.putExtra("currentCharacter", characterSelected);
        startActivity(characterDetailIntent);
    }


    private class HttpGetTask extends AsyncTask<Void, Void, List<ComicCharacter>> {
            AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
            @Override
            protected List<ComicCharacter> doInBackground(Void... params) {
                String name = searchEditText.getText().toString();
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
            //protected void onPostExecute(List<String> result) {
            protected void onPostExecute(List<ComicCharacter> result) {
                if (null != mClient)
                    mClient.close();
                setAdapter(result);
            }
     }

    private class JSONResponseHandler implements ResponseHandler<List<ComicCharacter>> {
     //private class JSONResponseHandler implements ResponseHandler<List<String>> {

            private static final String RESULTS_TAG = "results";
            private static final String NAME_TAG = "name";
            private static final String REAL_NAME_TAG = "real_name";
            private static final String ALIAS_TAG = "aliases";
            private static final String IMAGE_TAG = "image";
            private static final String ICON_URL = "icon_url";
            private static final String DETAILS_TAG  = "description";

            @Override
            //public List<String> handleResponse(HttpResponse response)
            public List<ComicCharacter> handleResponse(HttpResponse response)
                    throws ClientProtocolException, IOException {

                //List<String> result = new ArrayList<String>();
                List<ComicCharacter> result = new ArrayList<ComicCharacter>();
                String JSONResponse = new BasicResponseHandler()
                        .handleResponse(response);
                try {

                    JSONObject responseObject = (JSONObject) new JSONTokener(JSONResponse).nextValue();

                    JSONArray characters = responseObject
                            .getJSONArray(RESULTS_TAG);

                    for (int idx = 0; idx < characters.length(); idx++) {
                        JSONObject comicCharacter = (JSONObject) characters.get(idx);
                        String name = comicCharacter.get(NAME_TAG).toString();
                        String alias = comicCharacter.get(ALIAS_TAG).toString();
                        Object detailsObject = comicCharacter.get(DETAILS_TAG);
                        String details = "";
                        if(detailsObject != null) {
                            details = detailsObject.toString();
                        }

                        String url = "url";
                        try {
                            Object imageObject = comicCharacter.get(IMAGE_TAG);
                            if(imageObject!=null) {
                                JSONObject queEs = (JSONObject)imageObject;
                                url = queEs.get(ICON_URL).toString();
                            }
                        }catch(Exception ex) {
                            Log.e("ERROR ON PARSING",ex.getMessage());
                        }


                        ComicCharacter newCharacter = new ComicCharacter(name, alias, url, details);
                        result.add(newCharacter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }
}