package org.exoplatform.todo.service;

import java.util.List;

import org.exoplatform.todo.Todo;

public interface TodoService
{
	public void create(Todo todo) throws Exception;

	public List<Todo> getAll() throws Exception;

	public Todo getTodo(long id) throws Exception;

	public void update(Todo todo) throws Exception;

	public void delete(Todo todo) throws Exception;
}
