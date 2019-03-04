var contextRoot = null;

$(document).ready(function() {
	var begin = moment().startOf('week');
	console.log(begin.format());
	
	contextRoot = $('meta[name=contextRoot]').attr("content");
	console.log(contextRoot);

	var pickr = $("#meal-date").flatpickr({
		onChange : function(dates, str, pickr) {
			console.log(dates);
			reload();
		}
	});
	pickr.setDate(new Date());
	
	reload();
});

function reload() {
	console.log($("#meal-date").val());

	$.ajax({
        url : contextRoot+'/api/1.0/mealmenu?currentDate='+$("#meal-date").val(),
        success : function(data){
        	var html = '';
            if (data != null) {
            	var minSDay = null;
            	for (var i = 0; i < data.length; i++) {
            		if (data[i]['serviceDate'] != null) {
            			minSDay = moment(data[i]['serviceDate']);
            			break;
            		}
            	}

            	if (minSDay != null) {
            		var oriStartDay = minSDay.startOf('week');

            		//var rows = [];
            		for (var z = 0; z < 9; z++) {
            			//rows[z] = '<tr>';
            			html += '<tr>';
            			
    					var startDay = moment(oriStartDay);
	            		for (var i = 0; i < 7; i++) {
	        				if (z == 0)
	        					html += '<td>' + startDay.format('ddd') + '</td>';
	        				else if (z == 1)
	        					html += '<td>' + startDay.format('YYYY-MM-DD') + '</td>';
	        				else {
	        					console.log(startDay);
		            			var menu = null;
		            			for (var j = 0; j < data.length; j++) {
		            				if (startDay.isSame(data[j]['serviceDate'])) {
		            					menu = data[j];
		            					break;
		            				}
		            			}
		            			console.log(menu);
		            			if (menu != null) {
		            				if (z == 2)
		            					html += '<td>' + menu['salad1'] + '</td>';
		            				else if (z == 3)
		            					html += '<td>' + menu['salad2'] + '</td>';
		            				else if (z == 4)
		            					html += '<td>' + menu['mrice'] + '</td>';
		            				else if (z == 5)
		            					html += '<td>' + menu['dessert'] + '</td>';
		            				else if (z == 6) {
		            					html += '<td>' + menu['dessert'] + '</td>';
		            				} else
		            					html += '<td>.</td>';
		            			} else {
		            				html += '<td></td>';
		            			}
	        				}
	            			startDay.add(1, 'd');
	            		}
	            		html += '</tr>';
            		}
            	} else {
            		html += '<tr><td>no data.</td></tr>';
            	}
            	console.log();
            }
            html += '';
            
            $('#meal-menu').html(html);
        }
    })
}