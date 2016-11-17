$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#warehouseTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#warehouseBinTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#warehouseBinTable').dataTable().fnFilter('');
	});
});