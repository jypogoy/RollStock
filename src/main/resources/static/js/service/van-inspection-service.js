var VanInspectionService = angular.module('VanInspectionService', []);
VanInspectionService.factory('VanInspectionDataOp', ['$http', function($http) {
	
	var VanInspectionDataOp = {};
	
	VanInspectionDataOp.getVanInspections = function() {
		return $http.get('/api_van_inspections');
	}
	
	VanInspectionDataOp.getCount = function() {
		return $http.get('/api_van_inspections/count');
	}
	
	VanInspectionDataOp.addVanInspection = function(vanInspection) {
		return $http.post('/api_van_inspections', vanInspection);
	}
	
	VanInspectionDataOp.editVanInspection = function(vanInspection) {
		return $http.put('/api_van_inspections/', vanInspection);
	}
	
	VanInspectionDataOp.deleteVanInspection = function(id) {
		return $http.delete('/api_van_inspections/' + id);
	}
	
	return VanInspectionDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('VanInspectionService');