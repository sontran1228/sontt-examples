var todoApp = (function($) {
	var actions = {
		addTodo : function(newtodo) {
			var todoInput = $(newtodo).closest('#NewTodo').find('#NewTodoInput');
			var todoContent = $(todoInput).val();
			$(todoInput).val('');
		    if(!todoContent){
		    	return;
		    }
		    var todoPriority = $(todoInput).prev("#NewPriorityInput").val();
		    $(todoInput).prev("#NewPriorityInput").val(0);
	            
			$("#TodoApplication #ListTodos").append('<p class="Border-Background TodoDetail">\
					<a href="javascript:void(0)" onclick="todoApp.completeTodo(this);" class="tag-a-css CompleteTodo">incomplete</a>\
					<select class="Border-Background Priority">\
						<option value="0" '+ ((todoPriority == 0) ? "selected" : "") +' >None</option>\
						<option value="1" '+ ((todoPriority == 1) ? "selected" : "") +' >High</option>\
						<option value="2" '+ ((todoPriority == 2) ? "selected" : "") +' >Med</option>\
						<option value="3" '+ ((todoPriority == 3) ? "selected" : "") +' >Low</option>\
			        </select>\
			        <input class="Todo" value='+todoContent+' onkeydown="todoApp.editTodo(this);" \>\
			        <a href="javascript:void(0)" onclick="todoApp.removeTodo(this)" class="tag-a-css DeleteTodo">delete</a>\
			        <input type="hidden" value='+todoContent+' \>\
					</p>');
		},
		
		removeTodo : function(todo) {
			$(todo).closest('p').remove();
		},
		
		completeTodo : function(todo) {
			var status = $(todo).text();
			status = (status == 'incomplete') ? 'completed' : 'incomplete';
			var todoDetail = $(todo).closest('.TodoDetail'); 
			if(status == 'completed') {
				$(todo).css('font-style', 'italic');
				todoDetail.children('input.Todo').css({'text-decoration' : 'line-through', 'font-style' : 'italic'}).attr('disabled', 'disabled').blur();
				todoDetail.children('select.Priority').attr('disabled', 'disabled');
			} else {
				$(todo).css('font-style', 'normal');
				todoDetail.children('input.Todo').css({'text-decoration' : 'none', 'font-style' : 'normal'}).attr('disabled', null).blur();
				todoDetail.children('select.Priority').attr('disabled', null);
			}
			$(todo).text(status);
		},
		
		editTodo : function(editTodo) {
			if(event.keyCode == 27 || event.keyCode == 13) {
				var hiddenField = $(editTodo).closest('p.TodoDetail').children('input[type=hidden]');
				$(editTodo).blur();
				if(event.keyCode == 27 || (event.keyCode == 13 && !$(editTodo).val())) {
					$(editTodo).val($(hiddenField).val());
				} else {
					$(hiddenField).val($(editTodo).val());
				}
			}
		}
	};
	return actions;
})($);