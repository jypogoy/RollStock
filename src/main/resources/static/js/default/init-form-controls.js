$(document).ready(function() {	
	$('textarea').maxlength({
		alwaysShow: true,
		threshold: 10,
		warningClass: "label label-success",
		limitReachedClass: "label label-important",
		separator: ' of ',
		preText: 'You have ',
		postText: ' chars remaining.',
		validate: true
	});

	// Activate bootstrap-select plugin	
	$('.selectpicker').selectpicker({
		// set options here
	});
	
	// set the default focus
	$(this).find('[autofocus]:first').focus(); 
});