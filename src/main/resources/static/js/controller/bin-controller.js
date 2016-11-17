app.controller('BinController', function($scope, $uibModal, BinDataOp) {
	
	$scope.status;
	$scope.bins;
	
	getBins(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditBinModalCtrl for broadcast.
	$scope.$on('binUpdated', function() {
		BinDataOp.getBins()
			.success(function(bins) {				
				$scope.bins = bins;	
			})
			.error(function(error) {
				notifyError('Unable to load Bin data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewBinModalCtrl for broadcast.
	$scope.$on('binAdded', function() {
		BinDataOp.getBins()
			.success(function(bins) {
				$scope.bins = bins;	
			})
			.error(function(error) {
				notifyError('Unable to load Bin data: ' + error.message);
			});
	});
	
	function getBins() {		
		BinDataOp.getBins()
			.success(function(bins) {
				$scope.bins = bins;	
			})
			.error(function(error) {
				notifyError('Unable to load Bin data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var bin = $scope.bins[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Bin \'' + bin.name + '\'?',
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
		    		BinDataOp.deleteBin(bin.id)
						.success(function(generatedKey) {
							notifySuccess('Bin \'' + bin.name + '\' was successfully deleted.');
							$scope.bins.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Bin: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(bin) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/bin-modal.html',
		    controller: 'EditBinModalCtrl',
		    resolve: {
		    	bin: function () {
		    		return angular.copy(bin);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditBinModalCtrl', function($rootScope, $scope, $uibModalInstance, BinDataOp, WarehouseDataOp, bin, isNew) {
	
	$scope.isNew = isNew;
	$scope.bin = bin;
	$scope.warehouses;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	getWarehouses();
	
	function getWarehouses() {
		WarehouseDataOp.getWarehouses()
			.success(function(warehouses) {
				$scope.warehouses = warehouses;	
			})
			.error(function(error) {
				notifyError('Unable to load Warehouse data: ' + error.message);
			});
	}
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.binForm.$valid) {				
			$scope.bin = angular.copy(bin); // instantiate a new copy and not reference to the current record
			BinDataOp.editBin(bin)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Bin ' + bin.name + ' was successfully updated.');
					$rootScope.$broadcast('binUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Bin: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewBinRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/bin-modal.html',
		    controller: 'NewBinModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewBinModalCtrl', function($rootScope, $scope, $uibModalInstance, BinDataOp, WarehouseDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var bin = $scope.bin = getBinStruct();
	$scope.warehouses;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	getWarehouses();
	
	function getWarehouses() {
		WarehouseDataOp.getWarehouses()
			.success(function(warehouses) {
				$scope.warehouses = warehouses;	
			})
			.error(function(error) {
				notifyError('Unable to load Warehouse data: ' + error.message);
			});
	}
	
	// All selectors and the data for selector comes much later as the execution of the command $('select').selectpicker().
	// Use the following to refresh the selectpicker after the data have been received.
	$scope.$watch(function() {
		$("#warehouse").selectpicker('refresh');
	});
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.binForm.$valid) {		
			BinDataOp.addBin(bin)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					bin.id = generatedKey;
					notifySuccess('Bin ' + bin.name + ' was successfully created.');
					$rootScope.$broadcast('binAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Bin: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.binForm.$valid) {	
			$scope.bin = angular.copy(bin); // instantiate a new copy and not reference to the current record
			BinDataOp.addBin(bin)
				.success(function(generatedKey) {
					bin.id = generatedKey;		
					notifySuccess('Bin ' + bin.name + ' was successfully created.');					
					$rootScope.$broadcast('binAdded'); // broadcast the change to refresh the list
					$scope.bin = bin = getBinStruct(); // reset form
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Bin: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {		
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
});
