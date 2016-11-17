$(document).ready(function() {
	//$('#searchInput').focus();
	
	$('#forStorageTable').DataTable();
	
	/*$('#searchInput').on('keyup click', function() {
		$('#forStorageTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#forStorageTable').dataTable().fnFilter('');
	});*/
});

function getEntryStruct() {
	return {
		id: null, // Marks if record was previously recorded. Ensures that tickets will be issued only for new roll entries.
		part: null,
		number: null,
		description: null,
		grade: null,
		sized: null,
		weight: null,
		lineal: null,
		remarks: null,
		ticket: null,
		receipt: null,
		supplier: null,
		warehouse: null,
		bin : null
	};
}

function getDescriptionStruct() {
	return {
		id: null,
		text: null
	};
}