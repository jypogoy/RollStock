$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#workerTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#workerTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#workerTable').dataTable().fnFilter('');
	});
});

function getWorkerStruct() {
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