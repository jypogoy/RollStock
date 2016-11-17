var ReceivingService = angular.module('ReceiptService', []);
ReceivingService.factory('ReceiptDataOp', ['$http', function($http) {
	
	var ReceivingDataOp = {};
	
	ReceivingDataOp.getReceipts = function() {
		return $http.get('/api_receipts');
	}
	
	ReceivingDataOp.getReceiptById = function(id) {
		return $http.get('/api_receipts/' + id);
	}
	
	ReceivingDataOp.getCount = function(id) {
		return $http.get('/api_receipts/count');
	}
	
	ReceivingDataOp.addReceipt = function(receipt) {
		return $http.post('/api_receipts', receipt);
	}
	
	ReceivingDataOp.editReceipt = function(receipt) {
		return $http.put('/api_receipts/', receipt);
	}
	
	ReceivingDataOp.deleteReceipt = function(id) {
		return $http.delete('/api_receipts/' + id);
	}
	
	return ReceivingDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('ReceiptService');