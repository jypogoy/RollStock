var FileService = angular.module('FileService', []);
FileService.factory('FileDataOp', ['$http', function($http) {
	
	var FileDataOp = {};
	
	FileDataOp.getFiles = function() {
		return $http.get('/api_files');
	}
	
	FileDataOp.getCount = function() {
		return $http.get('/api_files/count');
	}
	
	FileDataOp.addFile = function(file) {
		return $http.post('/api_files', file);
	}
	
	FileDataOp.editFile = function(file) {
		return $http.put('/api_files/', file);
	}
	
	FileDataOp.deleteFile = function(id) {
		return $http.delete('/api_files/' + id);
	}
	
	return FileDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('FileService');