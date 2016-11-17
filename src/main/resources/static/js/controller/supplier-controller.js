app.controller('SupplierController', function($scope, $uibModal, SupplierDataOp) {
	
	$scope.status;
	$scope.suppliers;
	
	getSuppliers(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditSupplierModalCtrl for broadcast.
	$scope.$on('supplierUpdated', function() {
		SupplierDataOp.getSuppliers()
			.success(function(suppliers) {				
				$scope.suppliers = suppliers;	
			})
			.error(function(error) {
				notifyError('Unable to load Supplier data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewSupplierModalCtrl for broadcast.
	$scope.$on('supplierAdded', function() {
		SupplierDataOp.getSuppliers()
			.success(function(suppliers) {
				$scope.suppliers = suppliers;	
			})
			.error(function(error) {
				notifyError('Unable to load Supplier data: ' + error.message);
			});
	});
	
	function getSuppliers() {		
		SupplierDataOp.getSuppliers()
			.success(function(suppliers) {
				$scope.suppliers = suppliers;	
			})
			.error(function(error) {
				notifyError('Unable to load Supplier data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var supplier = $scope.suppliers[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Supplier \'' + supplier.name + '\'?',
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
		    		SupplierDataOp.deleteSupplier(supplier.id)
						.success(function(generatedKey) {
							notifySuccess('Supplier \'' + supplier.name + '\' was successfully deleted.');
							$scope.suppliers.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Supplier: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(supplier) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/supplier-modal.html',
		    controller: 'EditSupplierModalCtrl',
		    resolve: {
		    	supplier: function () {
		    		return angular.copy(supplier);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditSupplierModalCtrl', function($rootScope, $scope, $uibModalInstance, SupplierDataOp, supplier, isNew) {
	
	$scope.isNew = isNew;
	$scope.supplier = supplier;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.supplierForm.$valid) {				
			$scope.supplier = angular.copy(supplier); // instantiate a new copy and not reference to the current record
			SupplierDataOp.editSupplier(supplier)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Supplier ' + supplier.name + ' was successfully updated.');
					$rootScope.$broadcast('supplierUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Supplier: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewSupplierRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/supplier-modal.html',
		    controller: 'NewSupplierModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewSupplierModalCtrl', function($rootScope, $scope, $uibModalInstance, SupplierDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var supplier = $scope.supplier = getSupplierStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.supplierForm.$valid) {		
			SupplierDataOp.addSupplier(supplier)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					supplier.id = generatedKey;
					notifySuccess('Supplier ' + supplier.name + ' was successfully created.');
					$rootScope.$broadcast('supplierAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Supplier: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.supplierForm.$valid) {	
			$scope.supplier = angular.copy(supplier); // instantiate a new copy and not reference to the current record
			SupplierDataOp.addSupplier(supplier)
				.success(function(generatedKey) {
					$scope.$broadcast('show-errors-reset');
					supplier.id = generatedKey;		
					notifySuccess('Supplier ' + supplier.name + ' was successfully created.');					
					$rootScope.$broadcast('supplierAdded'); // broadcast the change to refresh the list
					$scope.supplier = supplier = getSupplierStruct(); // reset form
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Supplier: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {		
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
});
