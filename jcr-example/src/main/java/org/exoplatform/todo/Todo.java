package org.exoplatform.todo;

public class Todo
{
	private long id;
	private String title;
	private String content;
	private long status;
	private long linkedTodoId;
	private Todo linkedTodo;

	public Todo()
	{
		this(0, "", "", 0, -1);
	}

	public Todo(long id, String title, String content, long status, long linkedTodoId)
	{
		this(id, title, content, status, linkedTodoId, null);
	}

	public Todo(long id, String title, String content, long status,
			long linkedTodoId, Todo linkedTodo)
	{
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.status = status;
		this.linkedTodoId = linkedTodoId;
		this.linkedTodo = linkedTodo;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public long getStatus()
	{
		return status;
	}

	public void setStatus(long status)
	{
		this.status = status;
	}

	public long getLinkedTodoId()
	{
		return linkedTodoId;
	}

	public void setLinkedTodoId(long linkedTodoId)
	{
		this.linkedTodoId = linkedTodoId;
	}

	public Todo getLinkedTodo()
	{
		return linkedTodo;
	}

	public void setLinkedTodo(Todo linkedTodo)
	{
		this.linkedTodo = linkedTodo;
	}

}
