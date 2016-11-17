app.controller('CollectionController', function($scope, $uibModal, CollectionDataOp) {
	
	$scope.status;
	$scope.collections;
	
	getCollections(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditCollectionModalCtrl for broadcast.
	$scope.$on('collectionUpdated', function() {
		CollectionDataOp.getCollections()
			.success(function(collections) {				
				$scope.collections = collections;	
			})
			.error(function(error) {
				notifyError('Unable to load Collection data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewCollectionModalCtrl for broadcast.
	$scope.$on('collectionAdded', function() {
		CollectionDataOp.getCollections()
			.success(function(collections) {
				$scope.collections = collections;	
			})
			.error(function(error) {
				notifyError('Unable to load Collection data: ' + error.message);
			});
	});
	
	function getCollections() {		
		CollectionDataOp.getCollections()
			.success(function(collections) {
				$scope.collections = collections;	
			})
			.error(function(error) {
				notifyError('Unable to load Collection data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var collection = $scope.collections[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Collection \'' + collection.name + '\'?',
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
		    		CollectionDataOp.deleteCollection(collection.id)
						.success(function(generatedKey) {
							notifySuccess('Collection \'' + collection.name + '\' was successfully deleted.');
							$scope.collections.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Collection: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(collection) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/collection-modal.html',
		    controller: 'EditCollectionModalCtrl',
		    resolve: {
		    	collection: function () {
		    		return angular.copy(collection);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditCollectionModalCtrl', function($rootScope, $scope, $uibModalInstance, CollectionDataOp, CollectionTypeDataOp, collection, isNew) {
	
	$scope.isNew = isNew;
	$scope.collection = collection;
	$scope.collectionTypes;
	
	$scope.isNew = false;
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	getCollectionTypes();
	
	function getCollectionTypes() {
		CollectionTypeDataOp.getCollectionTypes()
			.success(function(collectionTypes) {
				$scope.collectionTypes = collectionTypes;	
			})
			.error(function(error) {
				notifyError('Unable to load Collection Type data: ' + error.message);
			});
	}
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.collectionForm.$valid) {				
			$scope.collection = angular.copy(collection); // instantiate a new copy and not reference to the current record
			CollectionDataOp.editCollection(collection)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Collection ' + collection.name + ' was successfully updated.');
					$rootScope.$broadcast('collectionUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Collection: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewCollectionRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/collection-modal.html',
		    controller: 'NewCollectionModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewCollectionModalCtrl', function($rootScope, $scope, $uibModalInstance, CollectionDataOp, CollectionTypeDataOp, isNew) {
	
	$scope.isNew = isNew;
	$scope.collectionTypes = getCollectionTypes();
	// Instantiate a blank record to be used by the form.
	var collection = $scope.collection = getCollectionStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	function getCollectionTypes() {
		CollectionTypeDataOp.getCollectionTypes()
			.success(function(collectionTypes) {
				$scope.collectionTypes = collectionTypes;	
			})
			.error(function(error) {
				notifyError('Unable to load CollectionType data: ' + error.message);
			});
	}
	
	// All selectors and the data for selector comes much later as the execution of the command $('select').selectpicker().
	// Use the following to refresh the selectpicker after the data have been received.
	$scope.$watch(function() {
		$("#collectionType").selectpicker('refresh');
	});
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.collectionForm.$valid) {		
			CollectionDataOp.addCollection(collection)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					collection.id = generatedKey;
					notifySuccess('Collection ' + collection.name + ' was successfully created.');
					$rootScope.$broadcast('collectionAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to insert Collection: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.collectionForm.$valid) {	
			$scope.collection = angular.copy(collection); // instantiate a new copy and not reference to the current record
			CollectionDataOp.addCollection(collection)
				.success(function(generatedKey) {
					collection.id = generatedKey;		
					notifySuccess('Collection ' + collection.name + ' was successfully created.');					
					$rootScope.$broadcast('collectionAdded'); // broadcast the change to refresh the list
					var currentCollectionType = collection.collectionType; // persist the current type to set as initial value for UX
					$scope.collection = collection = getCollectionStruct(); // reset form
					$scope.collection.collectionType = collection.collectionType = currentCollectionType; // restore persisted type					
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to insert Collection: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {								
		$uibModalInstance.dismiss('cancel');
	}
	
});
