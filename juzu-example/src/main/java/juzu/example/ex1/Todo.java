package juzu.example.ex1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Todo
{
   public int id;

   public String title;

   public boolean done;

   private static AtomicInteger atomicId = new AtomicInteger(100);

   public Todo(String title, boolean done)
   {
      this.id = atomicId.getAndIncrement();
      this.title = title;
      this.done = done;
   }

   private static final Map<Integer, Todo> todoList = new LinkedHashMap<Integer, Todo>();

   static
   {
      saveTodo(-1, "test1", false);
      saveTodo(-1, "test2", true);
      saveTodo(-1, "test3", false);
   }

   public static void deleteTodo(int id)
   {
      System.out.println("Delete Todo(id = " + id + ")");
      todoList.remove(id);
   }

   public static void completeAll(boolean done)
   {
      Iterator<Todo> _itr = todoList.values().iterator();
      while (_itr.hasNext())
      {
         Todo todo = _itr.next();
         todo.done = done;
      }
   }

   public static void deleteCompleted()
   {
      ArrayList<Integer> ids = new ArrayList<Integer>();
      Iterator<Todo> _itr = todoList.values().iterator();
      while (_itr.hasNext())
      {
         Todo todo = _itr.next();
         if (todo.done)
         {
            ids.add(todo.id);
         }
      }

      for (Integer _id : ids)
      {
         deleteTodo(_id.intValue());
      }
   }

   public static Todo saveTodo(int id, String title, boolean done)
   {
      Todo todo = todoList.get(id);
      if (todo == null)
      {
         todo = new Todo(title, done);
      }
      else
      {
         todo.title = title;
         todo.done = done;
      }
      System.out.println("save/update todo(id = " + todo.id + ", title = " + todo.title + ", completed = " + todo.done
         + ")");
      todoList.put(todo.id, todo);
      return todo;
   }

   public static JSONArray getTodos() throws Exception
   {
      JSONArray _jarr = new JSONArray();
      Iterator<Todo> _itr = todoList.values().iterator();
      while (_itr.hasNext())
      {
         Todo todo = _itr.next();
         _jarr.put(new JSONObject(todo.toJSONString(todo)));
      }
      return _jarr;
   }

   public String toJSONString(Todo todo)
   {
      StringBuffer sb = new StringBuffer();
      sb.append("{").append(JSONObject.quote("id")).append(":").append(todo.id).append(",")
         .append(JSONObject.quote("title")).append(":").append(JSONObject.quote(todo.title)).append(",")
         .append(JSONObject.quote("done")).append(":").append(todo.done).append("}");

      return sb.toString();
   }
}
