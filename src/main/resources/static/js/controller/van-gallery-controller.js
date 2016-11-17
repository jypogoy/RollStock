app.controller('VanGalleryController', function($scope, Upload, $uibModal) {
	
	$scope.files;
	$scope.file = getFileStruct();
	
	$scope.fileNameChanged = function(ele) {
		var files = ele.files;
		var l = files.length;	
		var namesArr = [];
		for (var i = 0; i < l; i++) {
			namesArr.push(files[i].name);	
			/*for(x in files[i]) {
				alert(x);
			}*/
			//alert(files[i].webkitRelativePath);
			$scope.file.name = files[i].name;
			$scope.file.type = files[i].type;
			$scope.file.size = files[i].size;	
			alert(angular.toJson($scope.file));
			//size
			//type
			//slice
			break;
		}				
	};
	
	$scope.uploadFile = function (e, reader, file, fileList, fileOjects, fileObj) {
		alert(angular.toJson(file));
    };
	
	$scope.gatherFiles = function(files) {
		$scope.files = files;
		alert(angular.toJson(files));
	};
	
	$scope.newUpload = function() {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/modal/van-photo-upload-modal.html',
		    controller: 'NewVanPhotoModalCtrl',
		    resolve: {
		    	isNew: function() {
		    		return true;
		    	}
		    }
		});	
	};
});

app.controller('NewVanPhotoCtrl', function($scope, $uibModal) {
	
})

app.controller('NewVanPhotoModalCtrl', function($rootScope, $scope, $uibModalInstance) {
	
	
	$scope.cancel = function() {	
		$scope.$broadcast('show-errors-reset');
		$uibModalInstance.dismiss('cancel');
	}
});