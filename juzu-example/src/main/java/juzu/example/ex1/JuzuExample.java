package juzu.example.ex1;

import juzu.Action;

import juzu.Route;

import juzu.Resource;

import juzu.plugin.ajax.Ajax;

import juzu.Response.Render;

import juzu.Response;

import juzu.Path;
import juzu.View;
import juzu.template.Template;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

public class JuzuExample
{
   @Inject
   @Path("index.gtmpl")
   juzu.example.ex1.templates.index index;

   @Inject
   @Path("newtodo.gtmpl")
   juzu.example.ex1.templates.newtodo newtodo;

   @Inject
   @Path("stats.gtmpl")
   juzu.example.ex1.templates.stats stats;

   @View
   @Route("/")
   public void index() throws IOException
   {
      List<Todo> todos = Todo.getTodos();
      index.with().todos(todos).render();
   }
   
   @Ajax
   @Resource
   @Route("/todoAdd")
   public void todoAdd(String id, String title, String completed) 
   {
      Todo _todo = Todo.saveTodo(Integer.parseInt(id), title, Boolean.parseBoolean(completed));
      newtodo.with().todo(_todo).render();
   }
   
   @Ajax
   @Resource
   @Route("/stats")
   public void getStats()
   {
      List<Todo> todos = Todo.getTodos();
      int _completedItems = 0, _remainingItems = 0;
      for (Todo _todo : todos)
      {
         if(_todo.completed)
            _completedItems++;
      }
      _remainingItems = todos.size() - _completedItems;
      stats.with().completedItems(_completedItems).remainingItems(_remainingItems).totalItems(todos.size()).render();
   }
   
   @Ajax
   @Resource
   @Route("/todoDelete")
   public void todoDelete(String id) {
      Todo.deleteTodo(Integer.parseInt(id));
   }
}
