package org.scystl.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.scystl.todolist.R;

/**
 * Created by Andrea on 30/06/2015.
 */
public class TodoCursorAdapter extends CursorAdapter {


    public TodoCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        String body = cursor.getString(cursor.getColumnIndexOrThrow("text"));
        tvBody.setText(body);
    }
}
