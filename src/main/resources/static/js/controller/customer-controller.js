app.controller('CustomerController', function($scope, $uibModal, CustomerDataOp) {
	
	$scope.status;
	$scope.customers;
	
	getCustomers(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditCustomerModalCtrl for broadcast.
	$scope.$on('customerUpdated', function() {
		CustomerDataOp.getCustomers()
			.success(function(customers) {				
				$scope.customers = customers;	
			})
			.error(function(error) {
				notifyError('Unable to load Customer data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewCustomerModalCtrl for broadcast.
	$scope.$on('customerAdded', function() {
		CustomerDataOp.getCustomers()
			.success(function(customers) {
				$scope.customers = customers;	
			})
			.error(function(error) {
				notifyError('Unable to load Customer data: ' + error.message);
			});
	});
	
	function getCustomers() {		
		CustomerDataOp.getCustomers()
			.success(function(customers) {
				$scope.customers = customers;	
			})
			.error(function(error) {
				notifyError('Unable to load Customer data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var customer = $scope.customers[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Customer \'' + customer.name + '\'?',
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
		    		CustomerDataOp.deleteCustomer(customer.id)
						.success(function(generatedKey) {
							notifySuccess('Customer \'' + customer.name + '\' was successfully deleted.');
							$scope.customers.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Customer: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(customer) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/customer-modal.html',
		    controller: 'EditCustomerModalCtrl',
		    resolve: {
		    	customer: function () {
		    		return angular.copy(customer);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditCustomerModalCtrl', function($rootScope, $scope, $uibModalInstance, CustomerDataOp, customer, isNew) {
	
	$scope.isNew = isNew;
	$scope.customer = customer;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.customerForm.$valid) {				
			$scope.customer = angular.copy(customer); // instantiate a new copy and not reference to the current record
			CustomerDataOp.editCustomer(customer)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Customer ' + customer.name + ' was successfully updated.');
					$rootScope.$broadcast('customerUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Customer: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewCustomerRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/customer-modal.html',
		    controller: 'NewCustomerModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewCustomerModalCtrl', function($rootScope, $scope, $uibModalInstance, CustomerDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var customer = $scope.customer = getCustomerStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.customerForm.$valid) {		
			CustomerDataOp.addCustomer(customer)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					customer.id = generatedKey;
					notifySuccess('Customer ' + customer.name + ' was successfully created.');
					$rootScope.$broadcast('customerAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Customer: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.customerForm.$valid) {	
			$scope.customer = angular.copy(customer); // instantiate a new copy and not reference to the current record
			CustomerDataOp.addCustomer(customer)
				.success(function(generatedKey) {
					$scope.$broadcast('show-errors-reset');
					customer.id = generatedKey;		
					notifySuccess('Customer ' + customer.name + ' was successfully created.');					
					$rootScope.$broadcast('customerAdded'); // broadcast the change to refresh the list
					$scope.customer = customer = getCustomerStruct(); // reset form
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Customer: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {	
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
});
