package juzu.example.ex1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Todo
{
   public int id;

   public String title;

   public boolean completed;

   private static AtomicInteger atomicId = new AtomicInteger(100);

   public Todo(String title, boolean completed)
   {
      this.id = atomicId.getAndIncrement();
      this.title = title;
      this.completed = completed;
   }

   private static final Map<Integer, Todo> todoList = new LinkedHashMap<Integer, Todo>();

   static
   {
      saveTodo(-1, "test1", false);
      saveTodo(-1, "test2", true);
      saveTodo(-1, "test3", false);
   }

   public static void updateTodo(int id, String title, boolean completed)
   {
      saveTodo(id, title, completed);
   }

   public static void createTodo(String title, boolean completed)
   {
      saveTodo(-1, title, completed);
   }

   public static void deleteTodo(int id)
   {
      System.out.println("Delete Todo(id = " + id +")");
      todoList.remove(id);
   }

   public static Todo saveTodo(int id, String title, boolean completed)
   {
      Todo todo = todoList.get(id);
      if (todo == null)
      {
         todo = new Todo(title, completed);
      }
      else
      {
         todo.title = title;
         todo.completed = completed;
      }
      System.out.println("put todo(id = " + todo.id + ", title = " + todo.title + ", completed = " + todo.completed
         + ")");
      todoList.put(todo.id, todo);
      return todo;
   }

   public static List<Todo> getTodos()
   {
      return new ArrayList<Todo>(todoList.values());
   }
}
