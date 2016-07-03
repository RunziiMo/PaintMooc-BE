var app = angular.module('paintmooc.controllers');

app.controller('NavigationCtrl', function ($rootScope, $scope, $modal, auth, $http, $location, $route) {
    var self = this;
    self.tab = function (route) {
        return $route.current && route === $route.current.controller;
    }

    $scope.credentials = {
        grant_type: "password",
        username: "",
        password: ""
    };

    $scope.login = function () {
        auth.authenticate($scope.credentials, function (authenticated, message) {
            self.info = message;
            $rootScope.closeProcessing();
        });
    }

    self.logout = function () {
        auth.clear();
    }

    $scope.showLoginForm = auth.showLoginForm;
});