app.controller('ReceiptController', function($scope, $location, $http, $routeParams, Constants, ReceiptDataOp, SupplierDataOp, TicketDataOp, RollDataOp, DescriptionDataOp) {
	
	$scope.isNew = true;
	$scope.isEdit = false;	
	$scope.receipt;
	$scope.entry = getEntryStruct();
	$scope.entries = [];	
	$scope.current = { supplier: null };
	$scope.suppliers;
	$scope.parts = Constants.Parts;
	$scope.descriptions;
	
	// Temporary holders
	$scope.indexForEdit;
	$scope.currentSupplier;
	$scope.currentPart;
	$scope.currentDescription;
	
	var forDelEntries = []; // Keeps track of removed entries on review/edit. Ensures inexpensive issuance of tickets per entry.
	
	// Receive parameter from showRecord action and get the appropriate record.
	if($routeParams.id != undefined || $routeParams.id > 0) {
		$scope.isNew = false;
		ReceiptDataOp.getReceiptById($routeParams.id)
		.success(function(receipt) {
			$scope.receipt = receipt;
			$scope.current.supplier = receipt.supplier;
			RollDataOp.getByReceipt(receipt.id)
			.success(function(rolls) {
				angular.forEach(rolls, function(roll, key) {
					$scope.entries.push(
						{
							id: roll.id,
							part: roll.part,
							rollNumber: roll.number,
							description: roll.description,
							grade: roll.grade,
							sized: roll.sized,
							weight: roll.weight,
							lineal: roll.lineal,
							remarks: roll.remarks,
							ticket: roll.ticket,
							receipt: roll.receipt
						}
					);							
				});
				$("#supplier").prop('disabled', true);
				$("#supplier").val(receipt.supplier.id).selectpicker('refresh');						
			})
			.error(function(error) {
				notifyError('Unable to retrieve Roll details: ' + error.message);
			});				
		})
		.error(function(error) {
			notifyError('Unable to retrieve Receipt details: ' + error.message);
		});
	}
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	// Load initial data selection.
	getSuppliers();
	getDescriptions();
	
	// All selectors and the data for selector comes much later as the execution of the command $('select').selectpicker().
	// Use the following to refresh the selectpicker after the data have been received.
	$scope.$watch(function() {
		$("#supplierName").selectpicker('refresh');
	});
	
	function getSuppliers() {
		SupplierDataOp.getSuppliers()
			.success(function(suppliers) {
				$scope.suppliers = suppliers;
			})
			.error(function(error) {
				notifyError('Unable to load supplier data: ' + error.message);
			});
	}
	
	function getDescriptions() {
		DescriptionDataOp.getDescriptions()
			.success(function(descriptions) {
				$scope.descriptions = descriptions;
			})
			.error(function(error) {
				notifyError('Unable to load roll description data: ' + error.message);
			});
	}
	
	$scope.start = function() {
		$("#supplier").prop('disabled', true);
		$("#supplier").val('').selectpicker('refresh');
		$("#part").focus();
	}
	
	$scope.addToList = function() {
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		if($scope.form.receiptForm.$valid) {	
			$scope.entries.push($scope.entry);
			$scope.resetForm(false);
			notifySuccess('New entry was successfully added.');
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.update = function(entry){
		$scope.entries[$scope.indexForEdit] = entry;
		$scope.resetForm();
		notifySuccess('Selected entry was successfully updated.');
	}
	
	$scope.resetForm = function(fullReset) {
		// Keep reference of the current Supplier and Part making them persistent.
		$scope.currentSupplier = $scope.entry.supplier;
		$scope.currentPart = $scope.entry.part;
		$scope.currentDescription = $scope.entry.description;
		
		$scope.isEdit = false;
		$scope.entry = getEntryStruct(); // clear all values		
	
		$scope.$broadcast('show-errors-reset');
		$scope.indexForEdit = null;
		if(fullReset) {
			$("#supplier").prop('disabled', false);
			$("#supplier").val('').selectpicker('refresh');
			$("#part").val('').selectpicker('refresh');		
			$("#description").val('').selectpicker('refresh');		
			$('#supplier').focus();
		} else {
			// Restore current Supplier and Part.
			$scope.entry.supplier = $scope.currentSupplier;
			$scope.entry.part = $scope.currentPart;
			$scope.entry.description = $scope.currentDescription;
			$('#rollNumber').focus();
		}
	}	
	
	$scope.saveAndNew = function() {
		var receipt = {
			supplier: $scope.current.supplier	
		};
		if($routeParams.id != undefined || $routeParams.id > 0) { // Perform edit to existing receipt using the passed ID.
			var success = true;
			angular.forEach(forDelEntries, function(entry, key) {
				RollDataOp.deleteRoll(entry.id)
				.success(function() {
					// Do nothing...
				})
				.error(function(error) {
					notifyError('Unable to delete Roll: ' + error.message);
					success = false;
				});
			});
			if(success) {
				angular.forEach($scope.entries, function(entry, key) {	
					if(entry.id == undefined || entry.id == 0) { // Record only new entries.	
						TicketDataOp.addTicket()
						.success(function(ticketId) {
							var roll = {
								part: entry.part.name,
								number: entry.rollNumber,
								description: entry.description,
								grade: entry.grade,
								sized: entry.sized,
								weight: entry.weight,
								lineal: entry.lineal,
								remarks: entry.remarks,							
								ticket: {
									id: ticketId
								},
								receipt: {
									id: $routeParams.id
								}
							};
							RollDataOp.addRoll(roll)
							.success(function() {								
								// Do nothing...
							})
							.error(function(error) {
								success = false;
								notifyError('Unable to create new Roll: ' + error.message);
							});
						})
						.error(function(error) {
							success = false;
							notifyError('Unable to create new Ticket: ' + error.message);
						});		
					} else { // Update whatever is remaining regardless of any changes.
						var roll = {
							id: entry.id,
							part: entry.part.name,
							number: entry.rollNumber,
							description: entry.description,
							grade: entry.grade,
							sized: entry.sized,
							weight: entry.weight,
							lineal: entry.lineal,
							remarks: entry.remarks,							
							ticket: entry.ticket,
							receipt: entry.receipt
						};
						RollDataOp.editRoll(roll)
						.success(function() {								
							// Do nothing...
						})
						.error(function(error) {
							success = false;
							notifyError('Unable to create new Roll: ' + error.message);
						});
					}
				});				
			}
			if(success) {
				notifySuccess('Receipt was successfully updated.');
				$scope.resetForm(true);
				$scope.entries = [];
			}
		} else {
			var success = true;
			ReceiptDataOp.addReceipt(receipt)
			.success(function(receiptId) {									
				angular.forEach($scope.entries, function(entry, key) {
					TicketDataOp.addTicket()
					.success(function(ticketId) {
						var roll = {
							part: entry.part.name,
							number: entry.rollNumber,
							description: entry.description,
							grade: entry.grade,
							sized: entry.sized,
							weight: entry.weight,
							lineal: entry.lineal,
							remarks: entry.remarks,							
							ticket: {
								id: ticketId
							},
							receipt: {
								id: receiptId
							}
						};
						RollDataOp.addRoll(roll)
						.success(function() {								
							// Do nothing...
						})
						.error(function(error) {
							success = false;
							notifyError('Unable to create new Roll: ' + error.message);
						});						
					})
					.error(function(error) {
						success = false;
						notifyError('Unable to create new Ticket: ' + error.message);
					});		
				});	
			})
			.error(function(error) {
				success = false;
				notifyError('Unable to create new Receipt: ' + error.message);
			});
			if(success) {
				notifySuccess('New receipt was successfully created.');
				$scope.resetForm(true);
				$scope.entries = [];
			}
		}
	}
	
	$scope.delete = function(index) {
		var entry = $scope.entries[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete this entry from Supplier \'' + $scope.current.supplier.name + '\'?',
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
		    		if(entry.id != undefined || entry.id > 0) {
		    			forDelEntries.push(entry); // Keep reference of all deleted original entries. Will be used on save function.
		    		}	
					$scope.entries.splice(index, 1); // remove the selected record from the data source
					$scope.resetForm();
					$('#catchAllBtn').click();		
					notifySuccess('Selected entry was successfully deleted.');
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.edit = function(index, entry) {
		$("#supplier").val($scope.current.supplier.id).selectpicker('refresh');
		$scope.entry.id = entry.id;
		$scope.entry.part = entry.part;
		$("#part").val(entry.part.id).selectpicker('refresh');
		$scope.entry.rollNumber = entry.rollNumber;
		$scope.entry.ticketNumber = entry.ticketNumber;
		$scope.entry.description = entry.description;
		$("#description").val(entry.description.id).selectpicker('refresh');
		$scope.entry.grade = entry.grade;
		$scope.entry.sized = entry.sized;
		$scope.entry.weight = entry.weight;
		$scope.entry.quantity = entry.quantity;
		$scope.entry.lineal = entry.lineal;
		$scope.entry.remarks = entry.remarks;
		$scope.entry.ticket = entry.ticket;
		$scope.entry.receipt = entry.receipt;
		$scope.isEdit = true;
		$scope.indexForEdit = index;
		$('#supplier').focus();
	}			
	
	$scope.cancel = function() {
		if($scope.entries.length > 0) {
			bootbox.confirm({
				title: 'Warning: This operation can\'t be undone.',
			    message: 'Received list is not empty. Are you sure you want to cancel this receipt?',
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
			    		$scope.resetForm(true);
			    		$scope.entries = [];
			    		$('#catchAllBtn')
			    		$('#catchAllBtn').click();
					} else {
			    		
			    	}		        
			    }
			});
		} else {
			$scope.resetForm(true);
			$scope.entries = [];
		}
	}
	
	$scope.close = function() {
		if($scope.entries.length > 0) {
			bootbox.confirm({
				title: 'Warning: This operation can\'t be undone.',
			    message: 'Received list is not empty. Are you sure you want to cancel this receipt?',
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
			    		$location.path('/receiving');
			    		$('#catchAllBtn').click();
					} else {
			    		
			    	}		        
			    }
			});
		} else {
			$location.path('/receiving');
		}
	}
});
