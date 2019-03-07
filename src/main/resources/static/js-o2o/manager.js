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
                $('#meal-create').html("수정");
                $('#dialog-add-title').html("메뉴 수정");

                var launch1 = null;
                var launch2 = null;
                var dinner = null;

                for (var k = 0; k < data.meals.length; k++) {
                    if (data.meals[k].mealType == 'LAUNCH_1') {
                        launch1 = data.meals[k];
                    } else if (data.meals[k].mealType == 'LAUNCH_2') {
                        launch2 = data.meals[k];
                    } else if (data.meals[k].mealType == 'DINNER') {
                        dinner = data.meals[k];
                    }
                }

                if (launch1 != null) {
                    for (var i = 1; i <= 7; i++) {
                        if (launch1['menu' + i] == null || launch1['menu' + i] == '') {
                            $('#table-l1-' + i).html('-');
                        } else {
                            $('#table-l1-' + i).html(launch1['menu' + i]);
                        }
                    }
                } else {
                    for (var i = 1; i <= 7; i++) {
                        $('#table-l1-' + i).html('-');
                    }
                }
                if (launch2 != null) {
                    for (var i = 1; i <= 7; i++) {
                        if (launch2['menu' + i] == null || launch2['menu' + i] == '') {
                            $('#table-l2-' + i).html('-');
                        } else {
                            $('#table-l2-' + i).html(launch2['menu' + i]);
                        }
                    }
                } else {
                    for (var i = 1; i <= 7; i++) {
                        $('#table-l2-' + i).html('-');
                    }
                }
            } else {
                $('#meal-create').html("추가");
                $('#dialog-add-title').html("메뉴 추가");
            }

			//$('#meal-menu').html(html);
		}
	});
}