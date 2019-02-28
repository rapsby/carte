$(document).ready(function() {
	$('.form-signin').submit(function(event) {
		console.log($(this) + ".submit() called.");
		//event.preventDefault();
		
		console.log($('#inputID').val());
		console.log($('#inputPassword').val());
		
		
	});
});