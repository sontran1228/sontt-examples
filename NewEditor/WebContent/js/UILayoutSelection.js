(function($) {
	layouttSelection = {
		init : function(ids) {
			ids = ids.join(",#");
			$('#' + ids).on("click", function(event) {
				var id = $(this).attr('id');
				switch (id) {
				case "":
					break;
				default:
					break;
				}
			});
		}
	};
	
	return layouttSelection;
})($);