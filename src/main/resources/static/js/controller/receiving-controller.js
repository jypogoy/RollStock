app.controller('ReceivingController', function($scope, $http, $location, ReceiptDataOp) {
	
	$scope.receipts;
	getReceipts();
	
	function getReceipts() {		
		ReceiptDataOp.getReceipts()
			.success(function(receipts) {
				$scope.receipts = receipts;	
			})
			.error(function(error) {
				notifyError('Unable to load receipt data: ' + error.message);
			});
	}
	
	$scope.showRecord = function(receipt) {
		$location.path('/receipt/' + receipt.id);
	}
	
	$scope.delete = function(index) {
		var receipt = $scope.receipts[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Receipt number \'' + ('00000' + receipt.id).slice(-6) + '\'?',
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
		    		ReceiptDataOp.deleteReceipt(receipt.id)
						.success(function(generatedKey) {
							$scope.receipts.splice(index, 1); // remove the selected record from the data source
							notifySuccess('Receipt number \'' + ('00000' + receipt.id).slice(-6) + '\' was successfully deleted.');
						})
						.error(function(error) {
							notifyError('Unable to delete Receipt: ' + error.message);
						});					
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
});
