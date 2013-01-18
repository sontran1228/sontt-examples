package juzu.example.todo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Todo
{
   public String id;

   public String content;

   public int priority;

   public boolean status;

   public Todo()
   {
   }

   public Todo(String content, int priority, boolean status)
   {
      this.id = "" + sequence.getAndIncrement();
      this.content = content;
      this.priority = priority;
      this.status = status;
   }

   private static final AtomicInteger sequence = new AtomicInteger(Math.abs(new Random().nextInt()));

   private static Map<String, Todo> todos = new LinkedHashMap<String, Todo>();

   public static Todo saveTodo(String id, String content, String priority, String status)
   {
      int prio = Integer.parseInt(priority);
      boolean st = Boolean.parseBoolean(status);
      Todo todo = null;

      if (id != null && id.toString().length() > 0 && !todos.containsKey(id))
      {
         todo = new Todo(content, prio, st);
      }
      else
      {
         todo = todos.get(id);
         todo.content = content;
         todo.priority = prio;
         todo.status = st;
      }

      return todo;
   }

   public static Todo removeTodo(Todo todo)
   {
      return todos.remove(todo.id);
   }

   public static Map<String, Todo> getTodoList()
   {
      return todos;
   }
}
