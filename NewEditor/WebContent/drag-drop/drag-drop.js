$(function() {
	uiDragDrop = function(dragObject, zonesInLayout) {
		var applicationId='';
		var zonesInLayoutString = zonesInLayout.join(',');
		var _dragOptions = {
			cursor: "move",
			helper : function(event){
				applicationId = $(this).attr('id');
				return $(this).clone();
			},
			start : function( event, ui ) {
				var _classes = $(this).attr('class');
				if(_classes.indexOf('ZoneA') > -1) {
					$('#zone1,#zone4').css('background-color','rgb(193, 230, 193)');
				} else if(_classes.indexOf('ZoneB') > -1) {
					$('#zone2,#zone3,#zone5,#zone6,#zone7,#zone8').css('background-color','rgb(193, 230, 193)');
				}
			},
			stop : function( event, ui ) {
				$('#zone1,#zone2,#zone3,#zone4,#zone5,#zone6,#zone7,#zone8').css('background-color','white');
			}
			
		};
		
		// init drag
		$('.' + dragObject).each(function() {
			var _classes = $(this).attr('class');
			if(_classes.indexOf('ZoneA') > -1) {
				_dragOptions.connectToSortable = "#zone1, #zone4";
			} else if(_classes.indexOf('ZoneB') > -1) {
				_dragOptions.connectToSortable = "#zone2,#zone3,#zone5,#zone6,#zone7,#zone8";
			}
			$(this).draggable(_dragOptions);
		});
		
		
		// init drop
		$(zonesInLayoutString).droppable().sortable({
			connectWith: zonesInLayoutString,
			placeholder: "ui-state-highlight",
			tolerance : "intersect",
			cursor: "move",
			start : function( event, ui )  {
				// change border color if item doesn't belong to this zone
				var _classes = ui.item.attr('class');
				if(_classes.indexOf('ZoneA') > -1) {
					$('#zone1,#zone4').css('background-color','rgb(193, 230, 193)');
				} else if(_classes.indexOf('ZoneB') > -1) {
					$('#zone2,#zone3,#zone5,#zone6,#zone7,#zone8').css('background-color','rgb(193, 230, 193)');
				}
			},
			over : function(event, ui) {
				$(this).css('height','auto');
			},
			out : function(event, ui) {
				$("#zone2,#zone3,#zone5,#zone6,#zone7,#zone8").css('height','auto');
				setHeight();
			},
			receive : function(event,ui) {
				var _classes = ui.item.attr('class'); 
				if($(this).attr('id') == 'zone1' || $(this).attr('id') == 'zone4') {
					if(_classes.indexOf('ZoneB') > -1) {
						$(ui.sender).sortable('cancel');
					}
				} else {
					if(_classes.indexOf('ZoneA') > -1) {
						$(ui.sender).sortable('cancel');
					}
				}
			},
			stop : function(event, ui) {
				if(!ui.item.hasClass('application')) {
					var _item = ui.item;
					_item.removeClass("DragObjectPortlet ui-draggable").addClass("application");
					_item.attr('id', applicationId + "-" + Math.floor(Math.random() * 1000));
				}
				$('#zone1,#zone2,#zone3,#zone4,#zone5,#zone6,#zone7,#zone8').css('background-color','white');
				$("#zone2,#zone3,#zone5,#zone6,#zone7,#zone8").css('height','auto');
				setHeight();
			}
		});
	};
	
	setHeight = function() {
		var _temp = 0;
		if(_temp < $("#zone2").height()) {
			_temp = $("#zone2").height();
		}
		if(_temp < $("#zone3").height()) {
			_temp = $("#zone3").height();
		}
		 $("#zone2,#zone3").css('height',_temp);
		
		_temp = 0;
		if(_temp <= $("#zone5").height()) {
			_temp = $("#zone5").height();
		}
		if(_temp <= $("#zone6").height()) {
			_temp = $("#zone6").height();
		}
		if(_temp <= $("#zone7").height()) {
			_temp = $("#zone7").height();
		}
		if(_temp <= $("#zone8").height()) {
			_temp = $("#zone8").height();
		}
		$("#zone5,#zone6,#zone7,#zone8").css('height',_temp);
	};
});