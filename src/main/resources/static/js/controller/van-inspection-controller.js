app.controller('VanInspectionController', function($scope, $location, $uibModal, VanInspectionDataOp) {
	
	$scope.status;
	$scope.vanInspections;
	
	getVanInspections(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditVanInspectionModalCtrl for broadcast.
	$scope.$on('vanInspectionUpdated', function() {
		VanInspectionDataOp.getVanInspections()
			.success(function(vanInspections) {				
				$scope.vanInspections = vanInspections;	
			})
			.error(function(error) {
				notifyError('Unable to load inspection data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewVanInspectionModalCtrl for broadcast.
	$scope.$on('vanInspectionAdded', function() {
		VanInspectionDataOp.getVanInspections()
			.success(function(vanInspections) {
				$scope.vanInspections = vanInspections;	
			})
			.error(function(error) {
				notifyError('Unable to load inspection data: ' + error.message);
			});
	});
	
	function getVanInspections() {		
		VanInspectionDataOp.getVanInspections()
			.success(function(vanInspections) {
				$scope.vanInspections = vanInspections;	
			})
			.error(function(error) {
				notifyError('Unable to load inspection data: ' + error.message);
			});
	}		
	
	$scope.delete = function(index) {
		var vanInspection = $scope.vanInspections[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete this inspection record?',
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
		    		VanInspectionDataOp.deleteVanInspection(vanInspection.id)
						.success(function(generatedKey) {
							notifySuccess('Inspection record was successfully deleted.');
							$scope.vanInspections.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete inspection record: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(vanInspection) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/van-inspection-modal.html',
		    controller: 'EditVanInspectionModalCtrl',
		    resolve: {
		    	vanInspection: function () {
		    		return angular.copy(vanInspection);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
	$scope.showGallery = function(vanInspection) {
		$location.path('/van_gallery/' + vanInspection.id);
	}
});

app.controller('EditVanInspectionModalCtrl', function($rootScope, $scope, $uibModalInstance, VanInspectionDataOp, TruckerDataOp, vanInspection, isNew) {
	
	$scope.isNew = isNew;
	$scope.truckers;
	$scope.vanInspection = vanInspection;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	getTruckers();
	
	function getTruckers() {
		TruckerDataOp.getTruckers()
		.success(function(truckers) {
			$scope.truckers = truckers;						
		})
		.error(function(error) {
			notifyError('Unable to retrieve Trucker records: ' + error.message);
		});
		$("#trucker").val(vanInspection.trucker.id).selectpicker('refresh');
	}
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.vanInspectionForm.$valid) {				
			$scope.vanInspection = angular.copy(vanInspection); // instantiate a new copy and not reference to the current record
			VanInspectionDataOp.editVanInspection(vanInspection)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Inspection record was successfully updated.');
					$rootScope.$broadcast('vanInspectionUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update inspection record: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewVanInspectionRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/van-inspection-modal.html',
		    controller: 'NewVanInspectionModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewVanInspectionModalCtrl', function($rootScope, $scope, $uibModalInstance, VanInspectionDataOp, TruckerDataOp, isNew) {
	
	$scope.isNew = isNew;
	$scope.truckers;
	$scope.currentTrucker;
	// Instantiate a blank record to be used by the form.
	var vanInspection = $scope.vanInspection = getVanInspectionStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	getTruckers();
	
	function getTruckers() {
		TruckerDataOp.getTruckers()
		.success(function(truckers) {
			$scope.truckers = truckers;			
		})
		.error(function(error) {
			notifyError('Unable to retrieve Trucker records: ' + error.message);
		});
	}
	
	$scope.start = function() {
		$('#truckNumber').focus();
	}
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.vanInspectionForm.$valid) {		
			VanInspectionDataOp.addVanInspection(vanInspection)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					vanInspection.id = generatedKey;
					notifySuccess('Inspection record was successfully created.');
					$rootScope.$broadcast('vanInspectionAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create inspection record: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.vanInspectionForm.$valid) {	
			$scope.vanInspection = angular.copy(vanInspection); // instantiate a new copy and not reference to the current record
			VanInspectionDataOp.addVanInspection(vanInspection)
				.success(function(generatedKey) {					
					vanInspection.id = generatedKey;		
					notifySuccess('Inspection record was successfully created.');					
					$rootScope.$broadcast('vanInspectionAdded'); // broadcast the change to refresh the list
					$scope.vanInspection = vanInspection = getVanInspectionStruct(); // reset form		
					resetForm()
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create inspection record: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {			
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
	function resetForm() {		
		$scope.$broadcast('show-errors-reset');		
		angular.forEach($scope.form.vanInspectionForm, function(value, key) {
			if (typeof value === 'object' && value.hasOwnProperty('$modelValue')) 
				value.$setUntouched();
		});		
		$("#trucker").val('').selectpicker('refresh');		
		$('#trucker').focus();		
	}
});
