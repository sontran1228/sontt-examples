package org.exoplatform.example;

import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.ValuesParam;

import java.util.Iterator;
import java.util.Map;

public class TodoServiceImpl implements TodoService
{

   public TodoServiceImpl(InitParams params)
   {
      if (params != null)
      {
         ValuesParam vp = params.getValuesParam("init-todos");
         Iterator<String> it = vp.getValues().iterator();
         while (it.hasNext())
         {
            String title = it.next();
            addTodo(title);
         }
      }
   }

   @Override
   public void addTodo(String title)
   {
      Todo.addTodo(title);
   }

   @Override
   public void updateTodo(int id, String title, boolean completed)
   {
      Todo.updateTodo(id, title, completed);
   }

   @Override
   public void deleteTodo(int id)
   {
      Todo.deleteTodo(id);
   }

   @Override
   public Map<Integer, Todo> getTodos()
   {
      return Todo.getTodos();
   }

}
