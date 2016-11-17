$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#collectionTypeTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#collectionTypeTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#collectionTypeTable').dataTable().fnFilter('');
	});
});

function getCollectionTypeStruct() {
	return {
		id: null,
		name: null,
		description: null,
		dateCreated: null
	};
}