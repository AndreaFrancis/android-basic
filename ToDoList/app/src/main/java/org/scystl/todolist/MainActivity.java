package org.scystl.todolist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

//import org.scystl.adapters.TodoCursorAdapter;
import org.scystl.database.TodoDAO;
import org.scystl.entities.Todo;

import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView mTodosListView;
    private ArrayAdapter<Todo> mTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Todo> todos = TodoDAO.getAll();
        mTodoAdapter = new ArrayAdapter<Todo>(this, R.layout.activity_todo_list, R.id.template,todos);
        mTodosListView.setAdapter(mTodoAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addTodo(View view) {
        new MaterialDialog.Builder(this).title(R.string.new_todo_title).
                customView(R.layout.activity_new_todo,true).
                positiveText(R.string.save)
                .negativeText(R.string.cancel).
                callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        View view = dialog.getCustomView();
                        EditText todoName = (EditText)view.findViewById(R.id.txt_Name);
                        insertTodo(todoName.getText().toString());
                    }
                }).show();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Todo selectedTodo =  (Todo)mTodosListView.getItemAtPosition(position);
        selectedTodo.delete();
        Toast.makeText(getApplicationContext(),
                R.string.deleted_todo_message, Toast.LENGTH_SHORT).show();
        mTodoAdapter.remove(selectedTodo);
        mTodoAdapter.notifyDataSetChanged();
    }

    private void insertTodo(String todo) {
        Todo newTodo = TodoDAO.insert(todo);
        mTodoAdapter.insert(newTodo, 0);
        mTodoAdapter.notifyDataSetChanged();

    }

    private void init() {
        mTodosListView = (ListView) findViewById(R.id.listView);
        mTodosListView.setOnItemClickListener(this);
    }
}
