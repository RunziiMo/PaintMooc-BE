var app = angular.module('paintmooc.controllers');

app.controller('CourseCtrl', function ($scope, $rootScope, $location, CourseFactory, UtilsFactory) {

    $scope.listAll = function () {
        $rootScope.showProcessing();
        CourseFactory.query(function (response) {
                console.log(response);
                $rootScope.closeProcessing();
                $scope.courses = response ? response : [];
            },
            function (error) {
                $rootScope.addError(error);
            }
        );
    }
    $scope.listAll();

    $scope.editCar = function (carId) {
        $location.path('/cars-edit/' + carId);
    }

    $scope.insertCourse = function () {
        $scope.courses.push(
            {}
        );
        // $location.path('/cars-insert');
    }
    $scope.deleteCar = function (car) {
        $rootScope.showProcessing();
        CourseFactory.delete({id: car.id},
            function () {
                $rootScope.closeProcessing();
                UtilsFactory.addAlert('success', 'Car deleted!');
                $scope.listAll();
            },
            function (error) {
                $rootScope.addError(error);
            }
        );
    }
});

