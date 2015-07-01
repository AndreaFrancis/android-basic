package org.scystl.database;

import com.activeandroid.query.Select;

import org.scystl.entities.Todo;
import org.scystl.todolist.MainActivity;

import java.util.List;

/**
 * Created by Andrea on 29/06/2015.
 */
public class TodoDAO {
    public static List<Todo> getAll() {
        return new Select().from(Todo.class).execute();
    }

    public static Todo insert(String text) {
        Todo newTodo = new Todo(text);
        newTodo.save();
        return newTodo;
    }
}
