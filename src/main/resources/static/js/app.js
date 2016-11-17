/*
 * ngRoute - performs routing of requests and display pages.
 * ui.bootstrap - makes use of all bootstrap interfaces e.g. modal, accordion, messages, etc.
 * ui.bootstrap.tpls - helps in loading custom templates i.e. created html pages.
 * SupplierService - exposes data access controls
 */
var app = angular.module('rollStockApp', ['ngRoute', 'ngFileUpload', 'naif.base64', 'ui.bootstrap', 'ui.bootstrap.tpls', 'ui.bootstrap.showErrors', 'datatables']);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider
	
	.when("/", {
		templateUrl: '/list/receiving-list.html',
		controller: 'ReceivingController'
	})
	
	.when("/dashboard", {
		templateUrl: 'dashboard.html',
		controller: 'DashboardController'
	})
	
	.when("/van_inspection", {
		templateUrl: '/list/van-inspection-list.html',
		controller: 'VanInspectionController'
	})
	
	.when("/van_gallery/:id", {
		templateUrl: '/detail/van-gallery.html',
		controller: 'VanGalleryController'
	})
	
	.when("/receiving", {
		templateUrl: '/list/receiving-list.html',
		controller: 'ReceivingController'
	})
	
	.when("/receipt", {
		templateUrl: '/detail/receipt.html',
		controller: 'ReceiptController'
	})
	
	.when("/receipt/:id", {
		templateUrl: '/detail/receipt.html',
		controller: 'ReceiptController'
	})
	
	.when("/storage", {
		templateUrl: '/list/storage-list.html',
		controller: 'StorageController'
	})
	
	.when("/descriptions", {
		templateUrl: '/list/description-list.html',
		controller: 'DescriptionController'
	})
	
	.when("/suppliers", {
		templateUrl: '/list/supplier-list.html'
	})
	
	.when("/customers", {
		templateUrl: '/list/customer-list.html'
	})
	
	.when("/truckers", {
		templateUrl: '/list/trucker-list.html'
	})
	
	.when("/workers", {
		templateUrl: '/list/worker-list.html'
	})
	
	.when("/warehouses", {
		templateUrl: '/list/warehouse-list.html'
	})
	
	.when("/warehouse_bins/:warehouseId", { // persist warehouse id to the next page
		templateUrl: '/list/warehouse-bins.html',
		controller: 'WarehouseBinController'
	})
	
	.when("/bins", {
		templateUrl: '/list/bin-list.html'
	})
	
	//------- Misc Routers ---------------------------------
	.when("/about", {
		templateUrl: 'about.html',
		controller: 'AboutController'
	})
	
	.when("/contact", {
		templateUrl: 'contact.html',
		controller: 'ContactController'
	})
	
	.when("/help", {
		templateUrl: 'help.html',
		controller: 'HelpController'
	})
	
	/*.otherwise({
		redirectTo: '/',
		controller: 'MainController'
	});*/
	
	// use the HTML5 History API to remove the # from URL
    //$locationProvider.html5Mode(true);
});

app.constant('Constants', {
	Parts: [
	    {id:1, name:'Body'}, 
	    {id:2, name:'Cover'}, 
	    {id:3, name:'Pad'}
	],
	
	RollDescriptions:[
	    {id:1, name:'KLB'}, 
	    {id:2, name:'KLB-MW'}, 
	    {id:3, name:'MWL'}, 
	    {id:4, name:'SCM'}
	]
});

//Transform the json data into the format expected by ng-options simply using Array.reduce
// With ng-options="key as val for (key, val) in parts" 
/*$scope.parts = Constants.Parts.reduce(function(label, obj) {
	return angular.extend(label, obj);
}, {});*/