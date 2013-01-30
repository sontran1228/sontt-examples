(function($) {
	uiDragDrop = {
		init : function(dragObject) {
			dragObject = dragObject.join(',.');
			var applicationId='';
			
			$('.' + dragObject).draggable({
				connectToSortable: '#zone1, #zone2, #zone3, #zone4',
				cursor: "move", 
				cursorAt: { 
					top: 56, left: 56
				},
				helper : function(event){
					applicationId = $(this).attr('id');
					return $(this).clone();
				},
				start : function(event,ui) {
					console.log('begin dragging ' + applicationId);
				},
				stop : function(event,ui) {
					console.log('end dragging');
				}
			});

			$('#zone1,#zone2,#zone3,#zone4').droppable({
				accept: ":not(.ui-sortable-helper)",
				drop : function(event, ui) {
					console.log('dropping..... ');
				}
			}).sortable({
				connectWith:'#zone1,#zone2,#zone3,#zone4',
				cursor: "move",
				stop : function(event,ui) {
					if(!ui.item.hasClass('application')) {
						var _item = ui.item;
						_item.removeClass("DragObjectPortlet ui-draggable").addClass("application");
						_item.attr('id', applicationId + "-" + Math.floor(Math.random() * 1000));
					}
					console.log('end sorting');
				}				
			});
		}
	};
	return uiDragDrop;
})($);
;