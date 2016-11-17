var VanImageService = angular.module('VanImageService', []);
VanImageService.factory('VanImageDataOp', ['$http', function($http) {
	
	var VanImageDataOp = {};
	
	VanImageDataOp.getVanImages = function() {
		return $http.get('/api_van_images');
	}
	
	VanImageDataOp.getCount = function() {
		return $http.get('/api_van_images/count');
	}
	
	VanImageDataOp.addVanImage = function(vanImage) {
		return $http.post('/api_van_images', vanImage);
	}
	
	VanImageDataOp.editVanImage = function(vanImage) {
		return $http.put('/api_van_images/', vanImage);
	}
	
	VanImageDataOp.deleteVanImage = function(id) {
		return $http.delete('/api_van_images/' + id);
	}
	
	return VanImageDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('VanImageService');