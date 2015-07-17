package org.scystl.comics;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

import org.scystl.comics.org.scystl.comics.model.ComicCharacter;


public class CharacterDetailActivity extends ActionBarActivity {

    private ComicCharacter currentCharacter;
    private TextView nameTextView;
    private TextView detailsTextView;
    private ImageView characterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent parentIntent = getIntent();
        this.currentCharacter = (ComicCharacter)parentIntent.getSerializableExtra("currentCharacter");
        setContentView(R.layout.activity_character_detail);
        this.nameTextView = (TextView)findViewById(R.id.name);
        this.nameTextView.setText(currentCharacter.getName());

        this.detailsTextView = (TextView)findViewById(R.id.details);
        this.detailsTextView.setText(currentCharacter.getDetails());

        characterImageView = (ImageView) findViewById(R.id.imageView);
        if(currentCharacter.getImageUrl() != null) {
            Picasso.with(this)
                    .load(currentCharacter.getImageUrl())
                    .error(R.drawable.ic_close_search)
                    .into(characterImageView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comic_detail, menu);
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
}
