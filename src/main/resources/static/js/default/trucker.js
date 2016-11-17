$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#truckerTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#truckerTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#truckerTable').dataTable().fnFilter('');
	});
});

function getTruckerStruct() {
	return {
		id: null,
		name: null,
		email: null,
		phoneMobile: null,
		phoneLandline: null,
		address: null,
		dateCreated: null
	};
}