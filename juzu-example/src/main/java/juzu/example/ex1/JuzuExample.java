package juzu.example.ex1;

import juzu.Path;
import juzu.Resource;
import juzu.Route;
import juzu.View;
import juzu.plugin.ajax.Ajax;
import juzu.template.Template;

import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

public class JuzuExample
{
   @Inject
   @Path("index.gtmpl")
   Template index;

   @Inject
   @Path("jsontmpl.gtmpl")
   juzu.example.ex1.templates.jsontmpl todoJSon;

   @View
   @Route("/")
   public void index() throws IOException
   {
      index.render();
   }

   @Ajax
   @Resource
   @Route("/getTodo")
   public void getTodo() throws Exception
   {
      todoJSon.with().todo(Todo.getTodos().toString()).render();
   }

   @Ajax
   @Resource
   @Route("/addTodo")
   public void addTodo(String model) throws Exception
   {
      JSONObject json = new JSONObject(model);
      Todo _todo = Todo.saveTodo(-1, json.getString("title"), json.getBoolean("done"));
      todoJSon.with().todo(_todo.toJSONString(_todo)).render();
   }

   @Ajax
   @Resource
   @Route("/editTodo")
   public void editTodo(String model) throws Exception
   {
      JSONObject json = new JSONObject(model);
      Todo _todo = Todo.saveTodo(json.getInt("id"), json.getString("title"), json.getBoolean("done"));
      todoJSon.with().todo(_todo.toJSONString(_todo)).render();
   }

   @Ajax
   @Resource
   @Route("/deleteTodo")
   public void deleteTodo(String id)
   {
      Todo.deleteTodo(Integer.parseInt(id));
      index.render();
   }

   @Ajax
   @Resource
   @Route("/completeAll")
   public void completeAll(String done) throws Exception
   {
      Todo.completeAll(Boolean.parseBoolean(done));
      todoJSon.with().todo(Todo.getTodos().toString()).render();
   }

   @Ajax
   @Resource
   @Route("/deleteCompleted")
   public void deleteCompleted() throws Exception
   {
      Todo.deleteCompleted();
      todoJSon.with().todo(Todo.getTodos().toString()).render();
   }
}
