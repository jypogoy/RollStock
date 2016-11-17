app.controller('WorkerController', function($scope, $uibModal, WorkerDataOp) {
	
	$scope.status;
	$scope.workers;
	
	getWorkers(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditWorkerModalCtrl for broadcast.
	$scope.$on('workerUpdated', function() {
		WorkerDataOp.getWorkers()
			.success(function(workers) {				
				$scope.workers = workers;	
			})
			.error(function(error) {
				notifyError('Unable to load Worker data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewWorkerModalCtrl for broadcast.
	$scope.$on('workerAdded', function() {
		WorkerDataOp.getWorkers()
			.success(function(workers) {
				$scope.workers = workers;	
			})
			.error(function(error) {
				notifyError('Unable to load Worker data: ' + error.message);
			});
	});
	
	function getWorkers() {		
		WorkerDataOp.getWorkers()
			.success(function(workers) {
				$scope.workers = workers;	
			})
			.error(function(error) {
				notifyError('Unable to load Worker data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var worker = $scope.workers[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Worker \'' + worker.name + '\'?',
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
		    		WorkerDataOp.deleteWorker(worker.id)
						.success(function(generatedKey) {
							notifySuccess('Worker \'' + worker.name + '\' was successfully deleted.');
							$scope.workers.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Worker: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(worker) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/worker-modal.html',
		    controller: 'EditWorkerModalCtrl',
		    resolve: {
		    	worker: function () {
		    		return angular.copy(worker);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditWorkerModalCtrl', function($rootScope, $scope, $uibModalInstance, WorkerDataOp, worker, isNew) {
	
	$scope.isNew = isNew;
	$scope.worker = worker;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.workerForm.$valid) {				
			$scope.worker = angular.copy(worker); // instantiate a new copy and not reference to the current record
			WorkerDataOp.editWorker(worker)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Worker ' + worker.name + ' was successfully updated.');
					$rootScope.$broadcast('workerUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Worker: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewWorkerRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/worker-modal.html',
		    controller: 'NewWorkerModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewWorkerModalCtrl', function($rootScope, $scope, $uibModalInstance, WorkerDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var worker = $scope.worker = getWorkerStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.workerForm.$valid) {		
			WorkerDataOp.addWorker(worker)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					worker.id = generatedKey;
					notifySuccess('Worker ' + worker.name + ' was successfully created.');
					$rootScope.$broadcast('workerAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Worker: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.workerForm.$valid) {	
			$scope.worker = angular.copy(worker); // instantiate a new copy and not reference to the current record
			WorkerDataOp.addWorker(worker)
				.success(function(generatedKey) {
					$scope.$broadcast('show-errors-reset');
					worker.id = generatedKey;		
					notifySuccess('Worker ' + worker.name + ' was successfully created.');					
					$rootScope.$broadcast('workerAdded'); // broadcast the change to refresh the list
					$scope.worker = worker = getWorkerStruct(); // reset form
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Worker: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
});
