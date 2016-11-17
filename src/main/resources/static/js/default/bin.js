$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#binTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#binTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#binTable').dataTable().fnFilter('');
	});
});

function getBinStruct() {
	return {
		id: null,
		name: null,
		length: null,
		width: null,
		height: null,
		warehouse: null,
		dateCreated: null
	};
}