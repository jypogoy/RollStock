$(document).ready(function() {
	$('#searchInput').focus();
	
	$('#customerTable').dataTable({
		'bFilter': false // hides search textbox
	});
	
	$('#searchInput').on('keyup click', function() {
		$('#inspectionTable').dataTable().fnFilter($(this).val());
	});
	
	$('#clearSearchBtn').on('click', function() {
		$('#searchInput').val('');
		$('#searchInput').focus();
		$('#inspectionTable').dataTable().fnFilter('');
	});
});

function getVanInspectionStruct() {
	return {
		id: null,
		trucker: null,
		truckNumber: null,
		vanNumber: null,
		remarks: null,
		inspectedBy: null,
		inspectionDate: null
	};
}