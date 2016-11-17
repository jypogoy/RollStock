var RollService = angular.module('RollService', []);
RollService.factory('RollDataOp', ['$http', function($http) {
	
	var RollDataOp = {};
	
	RollDataOp.getRolls = function() {
		return $http.get('/api_rolls');
	}
	
	RollDataOp.getRollsForStorage = function() {
		return $http.get('/api_rolls/for_storage');
	}
	
	RollDataOp.getByReceipt = function(receiptId) {
		return $http.get('/api_rolls/' + receiptId);
	}
	
	RollDataOp.getByBin = function(binId) {
		return $http.get('/api_rolls/bin/' + binId);
	}
	
	RollDataOp.addRoll = function(roll) {
		return $http.post('/api_rolls', roll);
	}
	
	RollDataOp.addMultipleRolls = function(rolls) {
		return $http.post('/api_rolls/multiple/', rolls);
	}
	
	RollDataOp.editRoll = function(roll) {
		return $http.put('/api_rolls/', roll);
	}
	
	RollDataOp.deleteRoll = function(id) {
		return $http.delete('/api_rolls/' + id);
	}
	
	RollDataOp.deleteRollByReceipt = function(receiptId) {
		return $http.delete('/api_rolls/by_receipt/' + receiptId);
	}
	
	return RollDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('RollService');