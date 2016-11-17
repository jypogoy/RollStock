app.controller('WarehouseController', function($rootScope, $scope, $uibModal, $location, WarehouseDataOp) {
	
	$scope.status;
	$scope.warehouses;
	
	getWarehouses(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditWarehouseModalCtrl for broadcast.
	$scope.$on('warehouseUpdated', function() {
		WarehouseDataOp.getWarehouses()
			.success(function(warehouses) {				
				$scope.warehouses = warehouses;	
			})
			.error(function(error) {
				notifyError('Unable to load Warehouse data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewWarehouseModalCtrl for broadcast.
	$scope.$on('warehouseAdded', function() {
		WarehouseDataOp.getWarehouses()
			.success(function(warehouses) {
				$scope.warehouses = warehouses;	
			})
			.error(function(error) {
				notifyError('Unable to load Warehouse data: ' + error.message);
			});
	});
	
	function getWarehouses() {		
		WarehouseDataOp.getWarehouses()
			.success(function(warehouses) {
				$scope.warehouses = warehouses;	
			})
			.error(function(error) {
				notifyError('Unable to load Warehouse data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var warehouse = $scope.warehouses[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Warehouse \'' + warehouse.name + '\'?',
		    buttons: {
		        confirm: {
		            label: 'Yes',
		            className: 'btn-success'
		        },
		        cancel: {
		            label: 'No',
		            className: 'btn-danger'
		        }
		    },
		    callback: function (result) {
		    	if(result) { // True
		    		WarehouseDataOp.deleteWarehouse(warehouse.id)
						.success(function(generatedKey) {
							notifySuccess('Warehouse \'' + warehouse.name + '\' was successfully deleted.');
							$scope.warehouses.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Warehouse: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(warehouse) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/warehouse-modal.html',
		    controller: 'EditWarehouseModalCtrl',
		    resolve: {
		    	warehouse: function () {
		    		return angular.copy(warehouse);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
	$scope.showBins = function(path, warehouse) {
		$location.path(path + '/' + warehouse.id);
		$location.replace();
	}
});

app.controller('EditWarehouseModalCtrl', function($rootScope, $scope, $uibModalInstance, WarehouseDataOp, warehouse, isNew) {
	
	$scope.isNew = isNew;
	$scope.warehouse = warehouse;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.warehouseForm.$valid) {				
			$scope.warehouse = angular.copy(warehouse); // instantiate a new copy and not reference to the current record
			WarehouseDataOp.editWarehouse(warehouse)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Warehouse ' + warehouse.name + ' was successfully updated.');
					$rootScope.$broadcast('warehouseUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Warehouse: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewWarehouseRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/warehouse-modal.html',
		    controller: 'NewWarehouseModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewWarehouseModalCtrl', function($rootScope, $scope, $uibModalInstance, WarehouseDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var warehouse = $scope.warehouse = getWarehouseStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.warehouseForm.$valid) {		
			WarehouseDataOp.addWarehouse(warehouse)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					warehouse.id = generatedKey;
					notifySuccess('Warehouse ' + warehouse.name + ' was successfully created.');
					$rootScope.$broadcast('warehouseAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Warehouse: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.warehouseForm.$valid) {	
			$scope.warehouse = angular.copy(warehouse); // instantiate a new copy and not reference to the current record
			WarehouseDataOp.addWarehouse(warehouse)
				.success(function(generatedKey) {
					$scope.$broadcast('show-errors-reset');
					warehouse.id = generatedKey;		
					notifySuccess('Warehouse ' + warehouse.name + ' was successfully created.');					
					$rootScope.$broadcast('warehouseAdded'); // broadcast the change to refresh the list
					$scope.warehouse = warehouse = getWarehouseStruct(); // reset form
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Warehouse: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
});
