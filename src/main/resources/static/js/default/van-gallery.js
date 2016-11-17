$(document).ready(function() {
	$(".file").fileinput();
});

function getFileStruct() {
	return {
		id: null,
		name: null,
		type: null,
		base64: null,
		dateCreated: null
	};
}