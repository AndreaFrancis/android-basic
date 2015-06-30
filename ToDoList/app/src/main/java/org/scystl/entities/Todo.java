package org.scystl.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Andrea on 27/06/2015.
 */
//Ver en : https://github.com/pardom/ActiveAndroid/wiki/Saving-to-the-database
@Table(name = "Todo" )
public class Todo extends Model {
    @Column (name = "text")
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
