var app = angular.module('paintmooc.controllers', []);

app.controller('CarsEditCtrl', function ($scope, $rootScope, $location, $routeParams, UserFactory, UtilsFactory) {
    $rootScope.showProcessing();
    $scope.car = UserFactory.get({id: $routeParams.id}, function () {
        $rootScope.closeProcessing();
    });

    $scope.cancel = function () {
        $location.path('/cars-list');
    }

    $scope.editCar = function () {
        $scope.submitted = true;
        if ($scope.myForm.$valid) {
            $rootScope.showProcessing();
            $scope.car.$update(
                function (response) {
                    $rootScope.closeProcessing();
                    UtilsFactory.addAlert('success', 'Car saved!');
                    $location.path('/cars-list');
                },
                function (error) {
                    $rootScope.addError(error);
                });
        } else {
            UtilsFactory.addAlert('danger', $rootScope.FORM_ERROR_ALERT);
        }
    }
});

