package org.exoplatform.example;

import java.util.Map;

public interface TodoService
{
   public void addTodo(String title);

   public void updateTodo(int id, String title, boolean completed);

   public void deleteTodo(int id);

   public Map<Integer, Todo> getTodos();
}
