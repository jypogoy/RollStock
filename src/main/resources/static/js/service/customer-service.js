var CustomerService = angular.module('CustomerService', []);
CustomerService.factory('CustomerDataOp', ['$http', function($http) {
	
	var CustomerDataOp = {};
	
	CustomerDataOp.getCustomers = function() {
		return $http.get('/api_customers');
	}
	
	CustomerDataOp.getCount = function() {
		return $http.get('/api_customers/count');
	}
	
	CustomerDataOp.addCustomer = function(customer) {
		return $http.post('/api_customers', customer);
	}
	
	CustomerDataOp.editCustomer = function(customer) {
		return $http.put('/api_customers/', customer);
	}
	
	CustomerDataOp.deleteCustomer = function(id) {
		return $http.delete('/api_customers/' + id);
	}
	
	return CustomerDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('CustomerService');