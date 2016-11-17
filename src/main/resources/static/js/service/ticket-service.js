var TicketService = angular.module('TicketService', []);
TicketService.factory('TicketDataOp', ['$http', function($http) {
	
	var TicketDataOp = {};
	
	TicketDataOp.getTickets = function() {
		return $http.get('/api_tickets');
	}
	
	TicketDataOp.addTicket = function(ticket) {
		return $http.post('/api_tickets');
	}
	
	TicketDataOp.editTicket = function(ticket) {
		return $http.put('/api_tickets/', ticket);
	}
	
	TicketDataOp.deleteTicket = function(id) {
		return $http.delete('/api_tickets/' + id);
	}
	
	return TicketDataOp;
}]);

//Add to the already created module's dependency. See app.js
app.requires.push('TicketService');