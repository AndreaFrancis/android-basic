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

import org.scystl.adapters.TodoCursorAdapter;
import org.scystl.database.TodoDAO;
import org.scystl.entities.Todo;

import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter<Todo> arrayAdapter;

    private void init() {
        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Todo> todos = TodoDAO.getAll();
        arrayAdapter = new ArrayAdapter<Todo>(this, R.layout.activity_todo_list, R.id.template,todos);
        listView.setAdapter(arrayAdapter);
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
        new MaterialDialog.Builder(this).title("Nueva tarea").customView(R.layout.activity_new_todo,true).positiveText("Guardar").negativeText("Cancelar").callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        View view = dialog.getCustomView();
                        EditText todoName = (EditText)view.findViewById(R.id.txt_Name);
                        Todo newTodo = TodoDAO.insert(todoName.getText().toString());
                        int position = arrayAdapter.getCount();
                        arrayAdapter.insert(newTodo, position);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }).show();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Todo selectedTodo =  (Todo)listView.getItemAtPosition(position);
        selectedTodo.delete();
        Toast.makeText(getApplicationContext(),
                "Se ha eliminado la tarea", Toast.LENGTH_SHORT).show();
        arrayAdapter.remove(selectedTodo);
        arrayAdapter.notifyDataSetChanged();

    }
}
