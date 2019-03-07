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

    $('#dialog-add').on('show.bs.modal', function (e) {
        $('#form-edit-l1-menu1').val('');
        $('#form-edit-l1-menu2').val('');
        $('#form-edit-l1-menu3').val('');
        $('#form-edit-salad1').val('');

        if (isEdit) {
            $.ajax({
                url : contextRoot + '/api/1.0/mealmenu?currentDate=' + $("#meal-date").val(),
                success : function(data) {
                    menuData = data;
                    if (data != null && data != '') {
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
                            $('#form-edit-l1-menu1').val(launch1['menu1']);
                            $('#form-edit-l1-menu2').val(launch1['menu2']);
                            $('#form-edit-l1-menu3').val(launch1['menu3']);
                        }
                        $('#form-edit-salad1').val(data['salad1']);
                    } else {
                    }
                }
            });
        } else {

        }
    });

    $('#form-edit-btn-save').click(function(e) {
        if (!isEdit) {
            menuData = {};
            menuData['serviceDate'] = $("#meal-date").val();
        }

        if (menuData['meals'] == null) {
            menuData['meals'] = [];
        }

        var launch1 = null;
        var launch2 = null;
        var dinner = null;

        for (var k = 0; k < menuData.meals.length; k++) {
            if (menuData.meals[k].mealType == 'LAUNCH_1') {
                launch1 = menuData.meals[k];
            } else if (menuData.meals[k].mealType == 'LAUNCH_2') {
                launch2 = menuData.meals[k];
            } else if (menuData.meals[k].mealType == 'DINNER') {
                dinner = menuData.meals[k];
            }
        }

        if (launch1 == null) {
            launch1 = {};
            launch1['mealType']= 'LAUNCH_1';

            menuData.meals.push(launch1);
        }
        launch1['menu1'] = $('#form-edit-l1-menu1').val();
        launch1['menu2'] = $('#form-edit-l1-menu2').val();
        launch1['menu3'] = $('#form-edit-l1-menu3').val();
        if (launch2 == null) {
            launch2 = {};
            launch2['mealType']= 'LAUNCH_2';

            menuData.meals.push(launch2);
        }
        if (dinner == null) {
            dinner = {};
            dinner['mealType']= 'DINNER';

            menuData.meals.push(dinner);
        }

        menuData['salad1'] = $('#form-edit-salad1').val();

        console.log(JSON.stringify(menuData));

        if (isEdit) {
            $.ajax({
                type: 'PUT',
                url:  contextRoot + '/api/1.0/mealmenu',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(menuData),
                dataType: 'json',
                success: function(data) {
                    console.log('good' + data);
                },
                complete: function() {
                    $('#dialog-add').modal('hide');
                    reload();
                }
            });
        } else {
            $.ajax({
                type: 'POST',
                url:  contextRoot + '/api/1.0/mealmenu',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(menuData),
                dataType: 'json',
                success: function(data) {
                    console.log('good' + data);
                },
                complete: function() {
                    $('#dialog-add').modal('hide');
                    reload();
                }
            });
        }
    });
    //

    reload();
});

var isEdit = false;
var menuData = null;

function reload() {
	console.log($("#meal-date").val());

	$.ajax({
		url : contextRoot + '/api/1.0/mealmenu?currentDate=' + $("#meal-date").val(),
		success : function(data) {
			var html = '';

			console.log(data);
            if (data != null && data != '') {
                isEdit = true;
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
                isEdit = false;
                $('#meal-create').html("추가");
                $('#dialog-add-title').html("메뉴 추가");
            }

			//$('#meal-menu').html(html);
		}
	});
}