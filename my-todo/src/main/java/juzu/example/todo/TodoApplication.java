package juzu.example.todo;

import juzu.Resource;

import juzu.plugin.ajax.Ajax;

import juzu.Path;
import juzu.Route;
import juzu.View;
import juzu.template.Template;

import java.io.IOException;

import javax.inject.Inject;

public class TodoApplication
{
   @Inject
   @Path("index.gtmpl")
   Template index;

   @View
   public void index() throws IOException
   {
      index("");
   }

   @View
   @Route("/result/{result}")
   public void index(String result)
   {
   }

   @Ajax
   @Resource
   @Route("/save")
   public void saveTodo(String id, String content, String priority, String status)
   {
      Todo todo = Todo.saveTodo(id, content, priority, status);
      
   }
}
