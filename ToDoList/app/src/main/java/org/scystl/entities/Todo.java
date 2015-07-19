package org.scystl.entities;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;



/**
 * Created by Andrea on 27/06/2015.
 */

@Table(name = "Todo" )
public class Todo extends Model {
    public static Cursor adapter;

    @Column (name = "text", unique = true)
    public String text;

    public Todo() {
        super();
    }

    public Todo(String text) {
        super();
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
