(function() {
	var Todo = function(id, content, priority, status) {
		this.id = id;
		this.content = content;
		this.priority = priority;
		this.status = status;
	};

	var TodoList = function() {
		var todosArr = new Array();

		var todoListFunc = {
			addTodo : function(id, content, priority, status) {
				todosArr.push(new Todo(id, content, priority, status));
			},

			removeTodo : function(todo) {
				var index = 0;
				for ( var i = 0; i < todosArr.length; i++) {
					if (todosArr[i].id == todo.id) {
						index = i;
					}
				}
				todosArr.splice(index, 1);
			},

			updateTodo : function(id, option) {
				for ( var i = 0; i < todosArr.length; i++) {
					if (todosArr[i].id == id) {
						todosArr[i].content = option.content;
						todosArr[i].status = option.status;
						todosArr[i].priority = option.priority;
					}
				}
			},

			updateListTodos : function(listTodos) {
				todosArr(0, todosArr.length);
				for ( var i = 0; i < listTodos.length; i++) {
					todosArr.push(listTodos[i]);
				}
			}
		};

		return todoListFunc;
	};

	return {
		'Todo' : Todo,
		'TodoList' : TodoList
	};
});