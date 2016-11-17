$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#descriptionTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#descriptionTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#descriptionTable').dataTable().fnFilter('');
	});
});

function getDescriptionStruct() {
	return {
		id: null,
		text: null,
		details: null,
		dateCreated: null
	};
}