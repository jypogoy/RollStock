$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#customerTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#customerTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#customerTable').dataTable().fnFilter('');
	});
});

function getCustomerStruct() {
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