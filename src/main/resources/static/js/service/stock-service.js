var StockService = angular.module('StockService', []);
StockService.factory('StockDataOp', ['$http', function($http) {
	
	var StockDataOp = {};
	
	StockDataOp.getStocks = function() {
		return $http.get('/api_stocks');
	}
	
	StockDataOp.getCount = function() {
		return $http.get('/api_stocks/count');
	}
	
	StockDataOp.addStock = function(stock) {
		return $http.post('/api_stocks', stock);
	}
	
	StockDataOp.editStock = function(stock) {
		return $http.put('/api_stocks/', stock);
	}
	
	StockDataOp.deleteStock = function(id) {
		return $http.delete('/api_stocks/' + id);
	}
	
	return StockDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('StockService');