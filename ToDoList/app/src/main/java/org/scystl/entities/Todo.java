package org.scystl.entities;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.scystl.adapters.TodoCursorAdapter;

/**
 * Created by Andrea on 27/06/2015.
 */
//Ver en : https://github.com/pardom/ActiveAndroid/wiki/Saving-to-the-database
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

    // Return cursor for result set for all todo items
    public static Cursor fetchResultCursor() {
        String tableName = Cache.getTableInfo(Todo.class).getTableName();
        // Query all items without any conditions
        String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(Todo.class).toSql();
        // Execute query on the underlying ActiveAndroid SQLite database
        Cursor resultCursor = Cache.openDatabase().rawQuery(resultRecords, null);

        return resultCursor;
    }

}
