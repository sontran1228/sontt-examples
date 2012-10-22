package org.exoplatform.todo.service.impl;

import java.util.List;

import junit.framework.TestCase;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.todo.Todo;
import org.exoplatform.todo.service.TodoService;

/**
 * Unit test for simple App.
 */
public class TodoServiceImplTest extends TestCase
{
	public void testCreate() throws Exception
	{
		System.out.println("------------testCreate()------------");
		PortalContainer container = PortalContainer.getInstance();
		TodoService todoService = (TodoService) container
				.getComponentInstanceOfType(TodoService.class);
		todoService.create(new Todo(1, "testTodo",
				"do example that use exo jcr 1111", 0, -1));

		todoService.create(new Todo(2, "testTodo2",
				"do example that use exo jcr 2222", 0, -1));

		todoService.create(new Todo(3, "testTod3",
				"do example that use exo jcr 3333", 0, -1));

	}

	public void testGetAll() throws Exception
	{
		System.out.println("------------testGetAll()------------");
		PortalContainer container = PortalContainer.getInstance();
		TodoService todoService = (TodoService) container
				.getComponentInstanceOfType(TodoService.class);
		List<Todo> todoList = todoService.getAll();

		assertEquals(3, todoList.size());
		for (int i = 0; i < todoList.size(); i++)
		{
			System.out.println(((Todo) todoList.get(i)).getContent());
		}
	}

	public void testUpdate() throws Exception
	{
		PortalContainer container = PortalContainer.getInstance();
		TodoService todoService = (TodoService) container
				.getComponentInstanceOfType(TodoService.class);
		todoService.update(new Todo(2, "testTodo2_update",
				"do example that use exo jcr 2222_update", 0, -1));
	}

	public void testGetTodo()
	{
		System.out.println("------------testGetTodo()------------");
		PortalContainer container = PortalContainer.getInstance();
		TodoService todoService = (TodoService) container
				.getComponentInstanceOfType(TodoService.class);
		try
		{
			Todo todo = todoService.getTodo(2);
			assertEquals(2, todo.getId());
			System.out.println(todo.getContent());
		} catch (Exception e)
		{
			fail("cannot find this todo");
			e.printStackTrace();
		}
	}

	public void testDelete() throws Exception
	{
		System.out.println("------------testDelete()------------");
		PortalContainer container = PortalContainer.getInstance();
		TodoService todoService = (TodoService) container
				.getComponentInstanceOfType(TodoService.class);
		todoService.delete(new Todo(2, "testTodo2_update",
				"do example that use exo jcr 2222_update", 0, -1));

		List<Todo> todoList = todoService.getAll();

		assertEquals(2, todoList.size());
		for (int i = 0; i < todoList.size(); i++)
		{
			System.out.println(((Todo) todoList.get(i)).getContent());
		}
	}
}
