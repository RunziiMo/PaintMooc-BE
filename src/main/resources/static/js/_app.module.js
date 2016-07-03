angular.module('paintmooc', [
        'ngRoute',
        'ui.bootstrap',
        'paintmooc.services',
        'paintmooc.controllers'
    ]).run(function (auth) {
        auth.init('/', '/login', '/logout');
    })
    .config(function ($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {templateUrl: 'views/home.html', controller: 'HomeCtrl'})
            .when('/course', {templateUrl: 'views/courses-list.html', controller: 'CourseCtrl'})
            .when('/painter', {templateUrl: 'views/painter-list.html', controller: 'PainterCtrl'})
            .when('/cars-edit/:id', {templateUrl: 'views/cars-edit.html', controller: 'CarsEditCtrl'})
            .when('/error', {templateUrl: 'views/error.html'})
            .otherwise('/');

    });