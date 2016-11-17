var TruckerService = angular.module('TruckerService', []);
TruckerService.factory('TruckerDataOp', ['$http', function($http) {
	
	var TruckerDataOp = {};
	
	TruckerDataOp.getTruckers = function() {
		return $http.get('/api_truckers');
	}
	
	TruckerDataOp.getCount = function() {
		return $http.get('/api_truckers/count');
	}
	
	TruckerDataOp.addTrucker = function(trucker) {
		return $http.post('/api_truckers', trucker);
	}
	
	TruckerDataOp.editTrucker = function(trucker) {
		return $http.put('/api_truckers/', trucker);
	}
	
	TruckerDataOp.deleteTrucker = function(id) {
		return $http.delete('/api_truckers/' + id);
	}
	
	return TruckerDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('TruckerService');