var contextRoot = null;

$(document).ready(function() {
	contextRoot = $('meta[name=contextRoot]').attr("content");
	if (!contextRoot)
		contextRoot = '';

    var pickr = $("#meal-date").flatpickr({
        onChange : function(dates, str, pickr) {
            reload();
        }
    });
    pickr.setDate(new Date());

    reload();
});

function reload() {
	console.log($("#meal-date").val());

	$.ajax({
		url : contextRoot + '/api/1.0/mealmenu?currentDate=' + $("#meal-date").val(),
		success : function(data) {
			var html = '';

			console.log(data);
            if (data != null && data != '') {
                $('#meal-edit').show();
                $('#meal-create').hide();
            } else {
                $('#meal-edit').hide();
                $('#meal-create').show();
            }

			//$('#meal-menu').html(html);
		}
	});
}