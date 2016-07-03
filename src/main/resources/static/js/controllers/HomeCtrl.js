var app = angular.module('paintmooc.controllers');

app.controller('HomeCtrl', function ($scope, $rootScope, $http) {

    $scope.showSelf = function () {
        $http.get('api/users/self').then(function (data) {

            console.log(data.data);
            $scope.user = data.data;
        });
    }
    if ($rootScope.authenticated) {
        $scope.showSelf();
    }
});

