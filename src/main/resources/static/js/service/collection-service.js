var CollectionService = angular.module('CollectionService', []);
CollectionService.factory('CollectionDataOp', ['$http', function($http) {
	
	var CollectionDataOp = {};
	
	CollectionDataOp.getCollections = function() {
		return $http.get('/api_collections');
	}
	
	CollectionDataOp.addCollection = function(collection) {
		return $http.post('/api_collections', collection);
	}
	
	CollectionDataOp.editCollection = function(collection) {
		return $http.put('/api_collections/', collection);
	}
	
	CollectionDataOp.deleteCollection = function(id) {
		return $http.delete('/api_collections/' + id);
	}
	
	return CollectionDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('CollectionService');