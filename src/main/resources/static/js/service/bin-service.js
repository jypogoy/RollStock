var BinService = angular.module('BinService', []);
BinService.factory('BinDataOp', ['$http', function($http) {
	
	var BinDataOp = {};
	
	BinDataOp.getBins = function() {
		return $http.get('/api_bins');
	}
	
	BinDataOp.getByWarehouse = function(warehouseId) {
		return $http.get('/api_bins/' + warehouseId);
	}
	
	BinDataOp.getAvailableByWarehouse = function(warehouseId) {
		return $http.get('/api_bins/available/' + warehouseId);
	}
	
	BinDataOp.getCount = function() {
		return $http.get('/api_bins/count');
	}
	
	BinDataOp.addBin = function(bin) {
		return $http.post('/api_bins', bin);
	}
	
	BinDataOp.editBin = function(bin) {
		return $http.put('/api_bins/', bin);
	}
	
	BinDataOp.deleteBin = function(id) {
		return $http.delete('/api_bins/' + id);
	}
	
	return BinDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('BinService');