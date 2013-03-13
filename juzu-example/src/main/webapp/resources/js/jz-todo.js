$(function() {
	$('#new-todo').on('keypress focusout', function(event) {
		var _keyCode = event.keyCode;

		if (event.type === "keypress") {
			if (event.keyCode != 13) {
				return;
			}
		}

		var _value = this.value;
		if (!_value)
			return;

		if( $('#todo-list').children('li').length > 0 ) {
			$('#todo-list').children('li').last().after('<li></li>');
		} else {
			$('#todo-list').append('<li></li>');
		}
		
		$('#todo-list').children('li').last().jzLoad("JuzuExample.todoAdd()", {
			"id" : -1,
			"title" : _value,
			"completed" : false
		});
		this.value = '';
	});
	
	$('#todo-list').children('li').each(function() {
		$(this).children('div.view').children('a.destroy').click(function() {
			var _destroy = $(this);
			var _itemId = _destroy.closest('div.view').attr('id');
			$('#todo-list').jzLoad("JuzuExample.todoDelete()", {
				"id" : _itemId
			});
			
			_destroy.closest('li').remove();
		});
	});
});