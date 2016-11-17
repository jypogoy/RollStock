$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#warehouseTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#warehouseTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#warehouseTable').dataTable().fnFilter('');
	});
});

function getWarehouseStruct() {
	return {
		id: null,
		name: null,
		description: null,
		address: null,
		bins: null,
		dateCreated: null
	};
}