package org.exoplatform.example;

import java.util.LinkedHashMap;
import java.util.Map;

public class Todo
{
   public int id;

   public String title;

   public boolean completed;

   private static final LinkedHashMap<Integer, Todo> todos = new LinkedHashMap<Integer, Todo>();

   public Todo(String title)
   {
      Integer[] ids = new Integer[todos.size()];
      todos.keySet().toArray(ids);
      if (todos.size() == 0)
      {
         this.id = 100;
      }
      else
      {
         this.id = ids[ids.length - 1].intValue() + 1;
      }

      this.title = title;
      this.completed = false;
   }

   public static void addTodo(String title)
   {
      Todo todo = new Todo(title);
      todos.put(todo.id, todo);
   }

   public static void updateTodo(int id, String title, boolean completed)
   {
      Todo todo = todos.get(id);
      todo.title = title;
      todo.completed = completed;
      todos.put(id, todo);
   }

   public static void deleteTodo(int id)
   {
      todos.remove(id);
   }

   public static Map<Integer, Todo> getTodos()
   {
      return todos;
   }

}
