app.controller('WarehouseBinController', function($scope, $routeParams, $uibModal, WarehouseDataOp, BinDataOp) {
	var warehouseId = $routeParams.warehouseId; // see app.js for warehouseId passed parameter
	$scope.warehouse;
	$scope.bins;
	
	getBins();
	
	function getBins() {
		WarehouseDataOp.getById(warehouseId)
			.success(function(warehouse) {
				$scope.warehouse = warehouse;
			})
			.error(function(error) {
				notifyError('Unable to load Warehouse data: ' + error.message);
			});
		
		BinDataOp.getByWarehouse(warehouseId)
			.success(function(bins) {
				$scope.bins = bins;	
			})
			.error(function(error) {
				notifyError('Unable to load Bin data: ' + error.message);
			});
	}
});