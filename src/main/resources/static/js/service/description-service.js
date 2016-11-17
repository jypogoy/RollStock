var DescriptionService = angular.module('DescriptionService', []);
DescriptionService.factory('DescriptionDataOp', ['$http', function($http) {
	
	var DescriptionDataOp = {};
	
	DescriptionDataOp.getDescriptions = function() {
		return $http.get('/api_descriptions');
	}
	
	DescriptionDataOp.getCount = function() {
		return $http.get('/api_descriptions/count');
	}
	
	DescriptionDataOp.addDescription = function(description) {
		return $http.post('/api_descriptions', description);
	}
	
	DescriptionDataOp.editDescription = function(description) {
		return $http.put('/api_descriptions/', description);
	}
	
	DescriptionDataOp.deleteDescription = function(id) {
		return $http.delete('/api_descriptions/' + id);
	}
	
	return DescriptionDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('DescriptionService');