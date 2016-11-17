var StorageService = angular.module('StorageService', []);
StorageService.factory('StorageDataOp', ['$http', function($http) {
	
	var StorageDataOp = {};
	
	StorageDataOp.getStorages = function() {
		return $http.get('/api_storages');
	}
	
	StorageDataOp.getCount = function() {
		return $http.get('/api_storages/count');
	}
	
	StorageDataOp.addStorage = function(storage) {
		return $http.post('/api_storages', storage);
	}
	
	StorageDataOp.editStorage = function(storage) {
		return $http.put('/api_storages/', storage);
	}
	
	StorageDataOp.deleteStorage = function(id) {
		return $http.delete('/api_storages/' + id);
	}
	
	return StorageDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('StorageService');