package org.scystl.todolist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import org.scystl.database.TodoDAO;
import org.scystl.entities.Todo;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private TextView textView;
    private List<Todo> todos;
    private ListView listView;

    private void init() {
        this.textView = (TextView) findViewById(R.id.txt_TodoText);
        this.todos = new ArrayList<>();
        this.listView = (ListView) findViewById(R.id.listView);
        List<Todo> todos = TodoDAO.getAll();
        ArrayAdapter<Todo> arryaAdapter = new ArrayAdapter<Todo>(this, R.layout.activity_todo_list, R.id.template,todos);
        listView.setAdapter(arryaAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void addTodo(View view) {
       /*
        String text = this.textView.getText().toString();
        Todo newTodo = new Todo(text);
        this.todos.add(newTodo);
        newTodo.save();
        Toast.makeText(getApplicationContext(),
                this.todos.size()+"", Toast.LENGTH_SHORT).show();
        */
        Intent newTodo = new Intent(this, NewTodoActivity.class);
        startActivity(newTodo);
    }


}
