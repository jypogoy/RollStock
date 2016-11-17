$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#receiptTable').dataTable({
		'bFilter': false // hides search textbox
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

function getEntryStruct() {
	return {
		id: null, // Marks if record was previously recorded. Ensures that tickets will be issued only for new roll entries.
		part: null,
		rollNumber: null,
		description: null,
		grade: null,
		sized: null,
		weight: null,
		lineal: null,
		remarks: null,
		ticket: null,
		receipt: null
	};
}

function getDescriptionStruct() {
	return {
		id: null,
		text: null
	};
}