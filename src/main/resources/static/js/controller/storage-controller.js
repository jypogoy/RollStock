app.controller('StorageController', function($scope, $location, $http, RollDataOp, WarehouseDataOp, BinDataOp, StorageDataOp) {
	
	$scope.tab = 1;
	$scope.rollsForStorage;
	$scope.warehouses = [];
	
	getRollsForStorage();
	
	// All selectors and the data for selector comes much later as the execution of the command $('select').selectpicker().
	// Use the following to refresh the selectpicker after the data have been received.
	$scope.$watch(function() {
		$('.selectpicker').selectpicker('refresh'); // Used the class for the meantime to get all combos.
	});
	
	function getRollsForStorage() {
		RollDataOp.getRollsForStorage()
		.success(function(rolls) {						
			$scope.rollsForStorage = rolls;			
		})
		.error(function(error) {
			notifyError('Unable to load roll data: ' + error.message);
		});			
		
		WarehouseDataOp.getWarehouses()
		.success(function(warehouses) {
			$scope.warehouses = warehouses;
		})
		.error(function(error) {
			notifyError('Unable to load warehouse data: ' + error.message);
		});
	}
	
	$scope.filterBins = function(index, roll) {
		BinDataOp.getAvailableByWarehouse(roll.warehouse.id)
		.success(function(bins) {
			var filteredBins = []; 
			angular.forEach(bins, function(bin, key) {
				filteredBins.push(bin); // Populate forcely injected bin collection.				
			});
			$scope.rollsForStorage[index].bins = filteredBins; // Forcely inject bin collection object to hold filtered records when selecting a warehouse within a row.
			if (bins.length > 0) {
				notifyInfo(bins.length + " available bin" + (bins.length > 1 ? "s" : "") + " found for warehouse '" + roll.warehouse.name + "'.");
			} else {
				notifyWarning("No available bins found for warehouse '" + roll.warehouse.name + "'.");
			}			
		})
		.error(function(error) {
			notifyError('Unable to load bin data: ' + error.message);
		});
	}
	
	$scope.setLocation = function(index, roll) {
		var storage = {
			roll: roll,
			bin: roll.bin,
			checkInBy: {} //TODO Apply necessary construction when user session is implemented.
		};
		
		StorageDataOp.addStorage(storage)
		.success(function() {
			getRollsForStorage();
			notifySuccess('Successfully assigned Roll # ' + roll.number + ' to Bin \'' + roll.bin.name + '\'.' );
		})
		.error(function(error) {
			notifyError('Unable to assign roll to bin: ' + error.message);
		});
	}
	
	$scope.removeFromLocation = function(roll) {
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to remove Roll # ' + roll.number + ' from Bin?',
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
		    		StorageDataOp.deleteStorage(roll.storage.id)
		    		.success(function() {
		    			getRollsForStorage();
		    			notifySuccess('Successfully removed Roll # ' + roll.number + ' from Bin.' );
		    		})
		    		.error(function(error) {
		    			notifyError('Unable to remove roll from bin: ' + error.message);
		    		});
		    	} else {
		    		
		    	}		        
		    }
		});		
	}
});