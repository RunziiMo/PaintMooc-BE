var app = angular.module('paintmooc.controllers');

app.controller('CarsInsertCtrl', function($scope, $rootScope, $location, CourseFactory, UtilsFactory){
	$scope.carro;
	
	$scope.cancel = function(){
		$location.path('/cars-list');
	}
	
	$scope.insertCar = function(){
		$scope.submitted = true;
		if ($scope.myForm.$valid) {
			$rootScope.showProcessing();
			CourseFactory.save($scope.car, 
					function(){
						$rootScope.closeProcessing();
						UtilsFactory.addAlert('success','Car saved!');
						$location.path('/cars-list');
					},
					function(error){
						$rootScope.addError(error);
					}
			);
		}else{
			UtilsFactory.addAlert('danger',$rootScope.FORM_ERROR_ALERT);
		}
	}
});

