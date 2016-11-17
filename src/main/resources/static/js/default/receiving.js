$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#receiptTable').dataTable({
		'bFilter': false, // hides search textbox
		'order': [[ 0, 'desc' ]]
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#receiptTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#receiptTable').dataTable().fnFilter('');
	});
});