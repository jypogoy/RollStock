app.controller('TicketController', function($scope, $uibModal, TicketDataOp) {
	
	$scope.status;
	$scope.tickets;
	
	getTickets(); // Initial call to load data.
	
	// Broadcast Listener: Reload controllers in case an edit occurred. See EditTicketModalCtrl for broadcast.
	$scope.$on('ticketUpdated', function() {
		TicketDataOp.getTickets()
			.success(function(tickets) {				
				$scope.tickets = tickets;	
			})
			.error(function(error) {
				notifyError('Unable to load Ticket data: ' + error.message);
			});
	});
	
	// Broadcast Listener: Reload controllers in case an create occurred. See NewTicketModalCtrl for broadcast.
	$scope.$on('ticketAdded', function() {
		TicketDataOp.getTickets()
			.success(function(tickets) {
				$scope.tickets = tickets;	
			})
			.error(function(error) {
				notifyError('Unable to load Ticket data: ' + error.message);
			});
	});
	
	function getTickets() {		
		TicketDataOp.getTickets()
			.success(function(tickets) {
				$scope.tickets = tickets;	
			})
			.error(function(error) {
				notifyError('Unable to load Ticket data: ' + error.message);
			});
	}
	
	$scope.delete = function(index) {
		var ticket = $scope.tickets[index]; // get record to delete
		bootbox.confirm({
			title: 'Warning: This operation can\'t be undone.',
		    message: 'Are you sure you want to delete Ticket \'' + ticket.name + '\'?',
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
		    		TicketDataOp.deleteTicket(ticket.id)
						.success(function(generatedKey) {
							notifySuccess('Ticket \'' + ticket.name + '\' was successfully deleted.');
							$scope.tickets.splice(index, 1); // remove the selected record from the data source
						})
						.error(function(error) {
							notifyError('Unable to delete Ticket: ' + error.message);
						});
		    	} else {
		    		
		    	}		        
		    }
		});
	}
	
	$scope.showRecord = function(ticket) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/ticket-modal.html',
		    controller: 'EditTicketModalCtrl',
		    resolve: {
		    	ticket: function () {
		    		return angular.copy(ticket);
		        },
		        isNew: function() {
		        	return false;
		        }
		    }
		});		
	};
	
});

app.controller('EditTicketModalCtrl', function($rootScope, $scope, $uibModalInstance, TicketDataOp, ticket, isNew) {
	
	$scope.isNew = isNew;
	$scope.ticket = ticket;
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 
	
	$scope.save = function() {
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.ticketForm.$valid) {				
			$scope.ticket = angular.copy(ticket); // instantiate a new copy and not reference to the current record
			TicketDataOp.editTicket(ticket)
				.success(function() {
					$uibModalInstance.close();
					notifySuccess('Ticket ' + ticket.name + ' was successfully updated.');
					$rootScope.$broadcast('ticketUpdated'); // broadcast the change to trigger next event
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to update Ticket: ' + error.message);
				});
		}	
	};
	
	$scope.cancel = function() {
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});

app.controller('NewTicketRecordCtrl', function($scope, $uibModal) {
	$scope.newRecord = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/ticket-modal.html',
		    controller: 'NewTicketModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
})

app.controller('NewTicketModalCtrl', function($rootScope, $scope, $uibModalInstance, TicketDataOp, isNew) {
	
	$scope.isNew = isNew;
	// Instantiate a blank record to be used by the form.
	var ticket = $scope.ticket = getTicketStruct();
	
	// Will register the input form here. HTML form name should be in form.formName format then call $scope.form.formName.
	$scope.form = {}; 	
	
	$scope.save = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity'); 
		
		if($scope.form.ticketForm.$valid) {		
			TicketDataOp.addTicket(ticket)
				.success(function(generatedKey) {
					$uibModalInstance.close();
					ticket.id = generatedKey;
					notifySuccess('Ticket ' + ticket.name + ' was successfully created.');
					$rootScope.$broadcast('ticketAdded'); // broadcast the change to refresh the list
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Ticket: ' + error.message);
				});
		} else {
			// implement focus to invalid field
		}
	};
	
	$scope.saveAndNext = function() {	
		
		// To execute validation even if not tabbed off, force the validity check.
		$scope.$broadcast('show-errors-check-validity');
		
		if($scope.form.ticketForm.$valid) {	
			$scope.ticket = angular.copy(ticket); // instantiate a new copy and not reference to the current record
			TicketDataOp.addTicket(ticket)
				.success(function(generatedKey) {
					$scope.$broadcast('show-errors-reset');
					ticket.id = generatedKey;		
					notifySuccess('Ticket ' + ticket.name + ' was successfully created.');					
					$rootScope.$broadcast('ticketAdded'); // broadcast the change to refresh the list
					$scope.ticket = ticket = getTicketStruct(); // reset form
					$('#name').focus();
				})
				.error(function(error) {
					$uibModalInstance.close();
					notifyError('Unable to create Ticket: ' + error.message)
				});
		} else {
			
		}	
	};
	
	$scope.cancel = function() {	
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
	
});
