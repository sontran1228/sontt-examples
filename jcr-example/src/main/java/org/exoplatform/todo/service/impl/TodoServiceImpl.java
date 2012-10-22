package org.exoplatform.todo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;

import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.todo.Todo;
import org.exoplatform.todo.service.TodoService;

public class TodoServiceImpl implements TodoService
{
	private RepositoryService repoService;

	public TodoServiceImpl(RepositoryService repoService)
	{
		this.repoService = repoService;
	}

	@Override
	public void create(Todo todo) throws Exception
	{
		ManageableRepository repository = repoService
				.getRepository("repository");
		Session session = repository.getSystemSession("production");

		Node rootNode = session.getRootNode();
		Node newNode = rootNode.addNode(String.valueOf(todo.getId()));
		newNode.setProperty("id", todo.getId());
		newNode.setProperty("title", todo.getTitle());
		newNode.setProperty("content", todo.getContent());
		newNode.setProperty("status", todo.getStatus());
		newNode.setProperty("linkedTodoId", todo.getLinkedTodoId());

		session.save();
		session.logout();
	}

	@Override
	public List<Todo> getAll() throws Exception
	{
		ManageableRepository repository = repoService
				.getRepository("repository");
		Session session = repository.getSystemSession("production");
		Node rootNode = session.getRootNode();

		List<Todo> todoList = new ArrayList<Todo>();
		// Get all nodes
		NodeIterator nodeIterator = rootNode.getNodes();
		while (nodeIterator.hasNext())
		{
			Node node = nodeIterator.nextNode();
			if ("jcr:system".equalsIgnoreCase(node.getName()))
			{
				continue;
			}
			long id = node.getProperty("id").getLong();
			String title = node.getProperty("title").getString();
			String content = node.getProperty("content").getString();
			long status = node.getProperty("status").getLong();
			long linkedTodoId = node.getProperty("linkedTodoId").getLong();
			todoList.add(new Todo(id, title, content, status, linkedTodoId));
		}

		session.logout();

		return todoList;
	}

	@Override
	public Todo getTodo(long id) throws Exception
	{
		ManageableRepository repository = repoService
				.getRepository("repository");
		Session session = repository.getSystemSession("production");
		Node rootNode = session.getRootNode();
		Node todoNode = rootNode.getNode(String.valueOf(id));
		long todoId = todoNode.getProperty("id").getLong();
		String title = todoNode.getProperty("title").getString();
		String content = todoNode.getProperty("content").getString();
		long status = todoNode.getProperty("status").getLong();
		long linkedTodoId = todoNode.getProperty("linkedTodoId").getLong();
		session.logout();

		return new Todo(todoId, title, content, status, linkedTodoId);
	}

	@Override
	public void update(Todo todo) throws Exception
	{
		ManageableRepository repository = repoService
				.getRepository("repository");
		Session session = repository.getSystemSession("production");
		Node rootNode = session.getRootNode();
		Node todoNode = rootNode.getNode(String.valueOf(todo.getId()));
		todoNode.getProperty("title").setValue(todo.getTitle());
		todoNode.getProperty("content").setValue(todo.getContent());
		todoNode.getProperty("status").setValue(todo.getStatus());
		todoNode.getProperty("linkedTodoId").setValue(todo.getLinkedTodoId());
		session.save();
		session.logout();
	}

	@Override
	public void delete(Todo todo) throws Exception
	{
		ManageableRepository repository = repoService
				.getRepository("repository");
		Session session = repository.getSystemSession("production");
		Node rootNode = session.getRootNode();
		Node todoNode = rootNode.getNode(String.valueOf(todo.getId()));
		todoNode.remove();

		session.save();
		session.logout();
	}
}
