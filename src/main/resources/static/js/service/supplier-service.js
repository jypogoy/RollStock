var SupplierService = angular.module('SupplierService', []);
SupplierService.factory('SupplierDataOp', ['$http', function($http) {
	
	var SupplierDataOp = {};
	
	SupplierDataOp.getSuppliers = function() {
		return $http.get('/api_suppliers');
	}
	
	SupplierDataOp.getCount = function() {
		return $http.get('/api_suppliers/count');
	}
	
	SupplierDataOp.addSupplier = function(supplier) {
		return $http.post('/api_suppliers', supplier);
	}
	
	SupplierDataOp.editSupplier = function(supplier) {
		return $http.put('/api_suppliers/', supplier);
	}
	
	SupplierDataOp.deleteSupplier = function(id) {
		return $http.delete('/api_suppliers/' + id);
	}
	
	return SupplierDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('SupplierService');