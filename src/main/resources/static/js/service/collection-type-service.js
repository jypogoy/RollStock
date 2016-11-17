var CollectionTypeService = angular.module('CollectionTypeService', []);
CollectionTypeService.factory('CollectionTypeDataOp', ['$http', function($http) {
	
	var CollectionTypeDataOp = {};
	
	CollectionTypeDataOp.getCollectionTypes = function() {
		return $http.get('/api_collectionType_types');
	}
	
	CollectionTypeDataOp.addCollectionType = function(collection) {
		return $http.post('/api_collectionType_types', collection);
	}
	
	CollectionTypeDataOp.editCollectionType = function(collection) {
		return $http.put('/api_collectionType_types/', collection);
	}
	
	CollectionTypeDataOp.deleteCollectionType = function(id) {
		return $http.delete('/api_collectionType_types/' + id);
	}
	
	return CollectionTypeDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('CollectionTypeService');