app.controller('DescriptionController', function($scope, $uibModal, DescriptionDataOp) {
	
	$scope.status;
	$scope.descriptions;
	
	getDescriptions(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditDescriptionModalCtrl for broadcast.
	$scope.$on('descriptionUpdated', function() {
		DescriptionDataOp.getDescriptions()
			.success(function(descriptions) {				
				$scope.descriptions = descriptions;	
			})
			.error(function(error) {
				notifyError('Unable to load Description data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewDescriptionModalCtrl for broadcast.
	$scope.$on('descriptionAdded', function() {
		DescriptionDataOp.getDescriptions()
			.success(function(descriptions) {
				$scope.descriptions = descriptions;	
			})
			.error(function(error) {
				notifyError('Unable to load Description data: ' + error.message);
			});
	});
	
	function getDescriptions() {		
		DescriptionDataOp.getDescriptions()
			.success(function(descriptions) {
				$scope.descriptions = descriptions;	
			})
			.error(function(error) {
				notifyError('Unable to load Description data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var description = $scope.descriptions[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Description \'' + description.text + '\'?',
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
		    		DescriptionDataOp.deleteDescription(description.id)
						.success(function(generatedKey) {
							notifySuccess('Description \'' + description.text + '\' was successfully deleted.');
							$scope.descriptions.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Description: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(description) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/description-modal.html',
		    controller: 'EditDescriptionModalCtrl',
		    resolve: {
		    	description: function () {
		    		return angular.copy(description);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditDescriptionModalCtrl', function($rootScope, $scope, $uibModalInstance, DescriptionDataOp, description, isNew) {
	
	$scope.isNew = isNew;
	$scope.description = description;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.descriptionForm.$valid) {				
			$scope.description = angular.copy(description); // instantiate a new copy and not reference to the current record
			DescriptionDataOp.editDescription(description)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Description ' + description.text + ' was successfully updated.');
					$rootScope.$broadcast('descriptionUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Description: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewDescriptionRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/description-modal.html',
		    controller: 'NewDescriptionModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewDescriptionModalCtrl', function($rootScope, $scope, $uibModalInstance, DescriptionDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var description = $scope.description = getDescriptionStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.descriptionForm.$valid) {		
			DescriptionDataOp.addDescription(description)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					description.id = generatedKey;
					notifySuccess('Description ' + description.text + ' was successfully created.');
					$rootScope.$broadcast('descriptionAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Description: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.descriptionForm.$valid) {	
			$scope.description = angular.copy(description); // instantiate a new copy and not reference to the current record
			DescriptionDataOp.addDescription(description)
				.success(function(generatedKey) {
					$scope.$broadcast('show-errors-reset');
					description.id = generatedKey;		
					notifySuccess('Description ' + description.text + ' was successfully created.');					
					$rootScope.$broadcast('descriptionAdded'); // broadcast the change to refresh the list
					$scope.description = description = getDescriptionStruct(); // reset form
					$('#text').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Description: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {	
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
});
