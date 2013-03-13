package juzu.example.ex1;

import juzu.impl.request.Request;

import juzu.impl.request.Parameter;

import juzu.request.RequestContext;

import org.json.JSONArray;
import org.json.JSONObject;

import juzu.impl.common.JSONParser;

import juzu.impl.common.JSON;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
      //List<Todo> todos = Todo.getTodos();
      index.render();
   }

   @Ajax
   @Resource
   @Route("/getTodo")
   public void getTodo() throws Exception
   {
      System.out.println("get all todos");
      todoJSon.with().todo(Todo.getTodos().toString()).render();//Response.ok(Todo.getTodos().toString());
   }

   @Ajax
   @Resource
   @Route("/addTodo")
   public void addTodo(String title, String order, String done)
   {
      Todo _todo = Todo.saveTodo(-1, title, Boolean.parseBoolean(done));
      todoJSon.with().todo(_todo.toJSONString(_todo)).render();
   }

   @Ajax
   @Resource
   @Route("/editTodo")
   public void editTodo(String id, String title, String done)
   {
      Todo _todo = Todo.saveTodo(Integer.parseInt(id), title, Boolean.parseBoolean(done));
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

   @Action
   @Route("/completeAll")
   public Response completeAll(String done)
   {
      boolean _done = false;
      if(done == null || done.trim().endsWith("null")) {
         _done = false;
      } else {
         _done = true;
      }
      Todo.completeAll(_done);
      return JuzuExample_.index();
   }
   
   @Action
   @Route("/deleteCompleted")
   public Response deleteCompleted()
   {
      Todo.deleteCompleted();
      return JuzuExample_.index();
   }
}
