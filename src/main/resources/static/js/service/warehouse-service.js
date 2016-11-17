var WarehouseService = angular.module('WarehouseService', []);
WarehouseService.factory('WarehouseDataOp', ['$http', function($http) {
	
	var WarehouseDataOp = {};
	
	WarehouseDataOp.getWarehouses = function() {
		return $http.get('/api_warehouses');
	}
	
	WarehouseDataOp.getById = function(id) {
		return $http.get('/api_warehouses/' + id);
	}
	
	WarehouseDataOp.getCount = function() {
		return $http.get('/api_warehouses/count');
	}
	
	WarehouseDataOp.addWarehouse = function(warehouse) {
		return $http.post('/api_warehouses', warehouse);
	}
	
	WarehouseDataOp.editWarehouse = function(warehouse) {
		return $http.put('/api_warehouses/', warehouse);
	}
	
	WarehouseDataOp.deleteWarehouse = function(id) {
		return $http.delete('/api_warehouses/' + id);
	}
	
	return WarehouseDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('WarehouseService');