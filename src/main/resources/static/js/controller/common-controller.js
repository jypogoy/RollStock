app.controller('MainController', function($scope, $location) {
	//$scope.message = 'Everyone come and see how good I look!';
});

app.controller('DashboardController', function($scope, $location) {
	$scope.message = 'You are in the dashboard!';
	$scope.isActive = function(path) {
		return path === $location.path();
	}
});

app.controller('SideBarController', function($scope, $location, BinDataOp, CustomerDataOp, 
			DescriptionDataOp, SupplierDataOp, TruckerDataOp, WarehouseDataOp, WorkerDataOp) { 
	
	$scope.descriptionCount = 0;
	$scope.supplierCount = 0;
	$scope.customerCount = 0;
	$scope.truckerCount = 0;
	$scope.workerCount = 0;
	$scope.warehouseCount = 0;
	$scope.binCount = 0;
	
	DescriptionDataOp.getCount()
	.success(function(count) {
		$scope.descriptionCount = count;
	})
	.error(function(error) {
		notifyError('Unable to get description count: ' + error.message);
	});	
	
	SupplierDataOp.getCount()
	.success(function(count) {
		$scope.supplierCount = count;
	})
	.error(function(error) {
		notifyError('Unable to get supplier count: ' + error.message);
	});	
	
	CustomerDataOp.getCount()
	.success(function(count) {
		$scope.customerCount = count;
	})
	.error(function(error) {
		notifyError('Unable to get customer count: ' + error.message);
	});	
	
	TruckerDataOp.getCount()
	.success(function(count) {
		$scope.truckerCount = count;
	})
	.error(function(error) {
		notifyError('Unable to get trucker count: ' + error.message);
	});	
	
	WorkerDataOp.getCount()
	.success(function(count) {
		$scope.workerCount = count;
	})
	.error(function(error) {
		notifyError('Unable to get worker count: ' + error.message);
	});	
	
	WarehouseDataOp.getCount()
	.success(function(count) {
		$scope.warehouseCount = count;
	})
	.error(function(error) {
		notifyError('Unable to get warehouse count: ' + error.message);
	});
	
	BinDataOp.getCount()
	.success(function(count) {
		$scope.binCount = count;
	})
	.error(function(error) {
		notifyError('Unable to get bin count: ' + error.message);
	});
	
	$scope.isActive = function(path) { // for highlighting active menu item
		return path === $location.path();
	}
});

