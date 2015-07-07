package org.scystl.comics;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

import org.scystl.comics.org.scystl.comics.model.ComicCharacter;

/**
 * Created by Andrea on 06/07/2015.
 */
public class CharactersAdapter extends BaseAdapter {


    Context parentContext;
    List<ComicCharacter> comicCharacterList;

    public CharactersAdapter(List<ComicCharacter> comicCharacterList, Context parentContext) {
        this.comicCharacterList = comicCharacterList;
        this.parentContext = parentContext;
    }

    @Override
    public int getCount() {
        return this.comicCharacterList.size();
    }

    @Override
    public ComicCharacter getItem(int position) {
        return this.comicCharacterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = ((Activity) parentContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.comic_template, parent, false);
        }
        ComicCharacter comicCharacter = this.comicCharacterList.get(position);

        TextView characterName = (TextView)convertView.findViewById(R.id.comic_description);
        TextView characterAliases = (TextView)convertView.findViewById(R.id.comic_name);

        String name = comicCharacter.getName() != null ? comicCharacter.getName() : "Not provided";
        String aliases = comicCharacter.getAlias() != null ? comicCharacter.getAlias() : "Not provided";

        characterName.setText(name);
        characterAliases.setText(aliases);
        return convertView;
    }
}
