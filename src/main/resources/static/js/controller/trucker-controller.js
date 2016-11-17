app.controller('TruckerController', function($scope, $uibModal, TruckerDataOp) {
	
	$scope.status;
	$scope.truckers;
	
	getTruckers(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditTruckerModalCtrl for broadcast.
	$scope.$on('truckerUpdated', function() {
		TruckerDataOp.getTruckers()
			.success(function(truckers) {				
				$scope.truckers = truckers;	
			})
			.error(function(error) {
				notifyError('Unable to load Trucker data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewTruckerModalCtrl for broadcast.
	$scope.$on('truckerAdded', function() {
		TruckerDataOp.getTruckers()
			.success(function(truckers) {
				$scope.truckers = truckers;	
			})
			.error(function(error) {
				notifyError('Unable to load Trucker data: ' + error.message);
			});
	});
	
	function getTruckers() {		
		TruckerDataOp.getTruckers()
			.success(function(truckers) {
				$scope.truckers = truckers;	
			})
			.error(function(error) {
				notifyError('Unable to load Trucker data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var trucker = $scope.truckers[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Trucker \'' + trucker.name + '\'?',
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
		    		TruckerDataOp.deleteTrucker(trucker.id)
						.success(function(generatedKey) {
							notifySuccess('Trucker \'' + trucker.name + '\' was successfully deleted.');
							$scope.truckers.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Trucker: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(trucker) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/trucker-modal.html',
		    controller: 'EditTruckerModalCtrl',
		    resolve: {
		    	trucker: function () {
		    		return angular.copy(trucker);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditTruckerModalCtrl', function($rootScope, $scope, $uibModalInstance, TruckerDataOp, trucker, isNew) {
	
	$scope.isNew = isNew;
	$scope.trucker = trucker;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.truckerForm.$valid) {				
			$scope.trucker = angular.copy(trucker); // instantiate a new copy and not reference to the current record
			TruckerDataOp.editTrucker(trucker)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Trucker ' + trucker.name + ' was successfully updated.');
					$rootScope.$broadcast('truckerUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Trucker: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewTruckerRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/trucker-modal.html',
		    controller: 'NewTruckerModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewTruckerModalCtrl', function($rootScope, $scope, $uibModalInstance, TruckerDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var trucker = $scope.trucker = getTruckerStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.truckerForm.$valid) {		
			TruckerDataOp.addTrucker(trucker)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					trucker.id = generatedKey;
					notifySuccess('Trucker ' + trucker.name + ' was successfully created.');
					$rootScope.$broadcast('truckerAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Trucker: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.truckerForm.$valid) {	
			$scope.trucker = angular.copy(trucker); // instantiate a new copy and not reference to the current record
			TruckerDataOp.addTrucker(trucker)
				.success(function(generatedKey) {
					$scope.$broadcast('show-errors-reset');
					trucker.id = generatedKey;		
					notifySuccess('Trucker ' + trucker.name + ' was successfully created.');					
					$rootScope.$broadcast('truckerAdded'); // broadcast the change to refresh the list
					$scope.trucker = trucker = getTruckerStruct(); // reset form
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Trucker: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {		
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
});
