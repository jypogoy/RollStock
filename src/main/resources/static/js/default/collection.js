$(document).ready(function() {
	$('#searchInput').focus();
	
	/*$('#collectionTable').dataTable({
		'bFilter': false // hides search textbox
	});*/
	
	$('#searchInput').on('keyup click', function() {
		$('#collectionTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#collectionTable').dataTable().fnFilter('');
	});
});

function getCollectionStruct() {
	return {
		id: null,
		name: null,
		description: null,
		collectionType: null,
		dateCreated: null
	};
}