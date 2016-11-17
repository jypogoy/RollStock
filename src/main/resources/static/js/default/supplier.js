$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#supplierTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#supplierTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#supplierTable').dataTable().fnFilter('');
	});
});

function getSupplierStruct() {
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