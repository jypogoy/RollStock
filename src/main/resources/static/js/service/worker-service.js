var WorkerService = angular.module('WorkerService', []);
WorkerService.factory('WorkerDataOp', ['$http', function($http) {
	
	var WorkerDataOp = {};
	
	WorkerDataOp.getWorkers = function() {
		return $http.get('/api_workers');
	}
	
	WorkerDataOp.getCount = function() {
		return $http.get('/api_workers/count');
	}
	
	WorkerDataOp.addWorker = function(worker) {
		return $http.post('/api_workers', worker);
	}
	
	WorkerDataOp.editWorker = function(worker) {
		return $http.put('/api_workers/', worker);
	}
	
	WorkerDataOp.deleteWorker = function(id) {
		return $http.delete('/api_workers/' + id);
	}
	
	return WorkerDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('WorkerService');