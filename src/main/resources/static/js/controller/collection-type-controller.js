app.controller('CollectionTypeController', function($scope, $uibModal, CollectionTypeDataOp) {
	
	$scope.status;
	$scope.collectionTypes;
	
	getCollectionTypes(); // Initial call to load data.
	
	// Broadcast Listener: Reload collectionTypes in case an edit occurred. See EditCollectionTypeModalCtrl for broadcast.
	$scope.$on('collectionTypeUpdated', function() {
		CollectionTypeDataOp.getCollectionTypes()
			.success(function(collectionTypes) {				
				$scope.collectionTypes = collectionTypes;	
			})
			.error(function(error) {
				notifyError('Unable to load Collection Type data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload collectionTypes in case an create occurred. See NewCollectionTypeModalCtrl for broadcast.
	$scope.$on('collectionTypeAdded', function() {
		CollectionTypeDataOp.getCollectionTypes()
			.success(function(collectionTypes) {
				$scope.collectionTypes = collectionTypes;	
			})
			.error(function(error) {
				notifyError('Unable to load Collection Type data: ' + error.message);
			});
	});
	
	function getCollectionTypes() {		
		CollectionTypeDataOp.getCollectionTypes()
			.success(function(collectionTypes) {
				$scope.collectionTypes = collectionTypes;	
			})
			.error(function(error) {
				notifyError('Unable to load Controller Type data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var collectionType = $scope.collectionTypes[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Collection Type \'' + collectionType.name + '\'?',
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
		    		CollectionTypeDataOp.deleteCollectionType(collectionType.id)
						.success(function(generatedKey) {
							notifySuccess('Collection Type \'' + collectionType.name + '\' was successfully deleted.');
							$scope.collectionTypes.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Collection Type: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(collectionType) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/collection-type-modal.html',
			controller: 'EditCollectionTypeModalCtrl',
		    resolve: {
		    	collectionType: function () {
		    		return angular.copy(collectionType);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditCollectionTypeModalCtrl', function($rootScope, $scope, $uibModalInstance, CollectionTypeDataOp, collectionType, isNew) {
	
	$scope.isNew = isNew;
	$scope.collectionType = collectionType;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.collectionTypeForm.$valid) {				
			$scope.collectionType = angular.copy(collectionType); // instantiate a new copy and not reference to the current record
			CollectionTypeDataOp.editCollectionType(collectionType)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Collection Type \'' + collectionType.name + '\' was successfully updated.');
					$rootScope.$broadcast('collectionTypeUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Collection Type: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewCollectionTypeRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/collection-type-modal.html',
			controller: 'NewCollectionTypeModalCtrl',
			resolve: {
		    	isNew: function() {
		        	return true;
		        }
		    }
		});	
	};
})

app.controller('NewCollectionTypeModalCtrl', function($rootScope, $scope, $uibModalInstance, CollectionTypeDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var collectionType = $scope.collectionType = getCollectionTypeStruct(); 
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 

		if($scope.form.collectionTypeForm.$valid) {			
			CollectionTypeDataOp.addCollectionType(collectionType)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					collectionType.id = generatedKey;
					notifySuccess('Collection Type \'' + collectionType.name + '\' was successfully created.');
					$rootScope.$broadcast('collectionTypeAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to insert CollectionType: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.collectionTypeForm.$valid) {	
			$scope.collectionType = angular.copy(collectionType); // instantiate a new copy and not reference to the current record
			CollectionTypeDataOp.addCollectionType($scope.collectionType)
				.success(function(generatedKey) {
					collectionType.id = generatedKey;		
					notifySuccess('CollectionType \'' + $scope.collectionType.name + '\' was successfully created.');					
					$rootScope.$broadcast('collectionTypeAdded'); // broadcast the change to refresh the list
					$scope.collectionType = collectionType = getCollectionTypeStruct(); // reset form
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to insert Collection Type: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {					
		$uibModalInstance.dismiss('cancel');
	}
	
});
