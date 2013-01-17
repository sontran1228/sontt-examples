(function($) {
	$(document).ready(function(){
		$("#TodoApplication #NewTodo").on("keypress", "#NewTodoInput", function(event) {
			if(event.keyCode != 13) {
				return;
			}
			var todoContent = $(this).val();
			if(!todoContent){
				return;
			}
			var todoPriority = $(this).prev("#NewPriorityInput").val();
			$(this).prev("#NewPriorityInput").val(0);
			
			
			$("#TodoApplication #ListTodos").append('<p>\
					<label class="CompleteTodo Border-Background">incomplete</label>\
					<select class="Priority Border-Background">\
						<option value="0" '+ ((todoPriority == 0) ? "selected" : "") +' >None</option>\
						<option value="1" '+ ((todoPriority == 1) ? "selected" : "") +' >High</option>\
						<option value="2" '+ ((todoPriority == 2) ? "selected" : "") +' >Med</option>\
						<option value="3" '+ ((todoPriority == 3) ? "selected" : "") +' >Low</option>\
			        </select>\
			        <label class="Todo Border-Background">'+todoContent+'</label>\
			        <label class="DeleteTodo Border-Background">delete</label>\
					</p>');
			$(this).val('');
		});
	});
}(jQuery));