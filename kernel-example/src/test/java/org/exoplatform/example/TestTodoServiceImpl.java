package org.exoplatform.example;

import junit.framework.TestCase;

import org.exoplatform.container.PortalContainer;

public class TestTodoServiceImpl extends TestCase
{

   private TodoService service;

   public void setUp()
   {
      PortalContainer pContainer = PortalContainer.getInstance();
      service = (TodoService)pContainer.getComponentInstanceOfType(TodoServiceImpl.class);
   }

   public void tearDown() throws Exception
   {
      Integer[] ids = new Integer[service.getTodos().size()];
      service.getTodos().keySet().toArray(ids);
      for (int i = 0; i < ids.length; i++)
      {
         service.deleteTodo(ids[i].intValue());
      }
   }

   public void testInitTodoParams()
   {
      System.out.println("==== testInitTodoParams() =====");
      assertEquals("initTodo1", service.getTodos().get(100).title);
      assertEquals(3, service.getTodos().size());
   }

   public void testAddTodo()
   {
      System.out.println("==== testAddTodo() =====");
      service.addTodo("test0"); // id: 100
      service.addTodo("test1"); // id: 101

      assertEquals(2, service.getTodos().size());
      assertEquals("test0", service.getTodos().get(100).title);
   }

   public void testUpdateTodo()
   {
      System.out.println("==== testUpdateTodo() =====");
      service.addTodo("test0"); // id: 100
      service.addTodo("test1"); // id: 101

      service.updateTodo(100, "test_100", false);
      service.updateTodo(101, "test_101", true);

      assertEquals("test_100", service.getTodos().get(100).title);
      assertTrue(service.getTodos().get(101).completed);
   }

   public void testDeleteTodo()
   {
      System.out.println("==== testDeleteTodo() =====");
      service.addTodo("test0"); // id: 100
      service.addTodo("test1"); // id: 101
      service.addTodo("test2"); // id: 102
      service.addTodo("test3"); // id: 103

      service.deleteTodo(102);

      assertFalse(service.getTodos().containsKey(102));
      assertEquals(3, service.getTodos().size());
   }

   public void testGetTodos()
   {
      System.out.println("==== testGetTodos() =====");
      service.addTodo("test0"); // id: 100
      service.addTodo("test1"); // id: 101
      service.addTodo("test2"); // id: 102
      service.addTodo("test3"); // id: 103

      assertEquals(4, service.getTodos().size());
   }
}
