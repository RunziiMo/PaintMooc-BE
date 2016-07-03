var app = angular.module('paintmooc.services');
/**
 * Factory to interact with the REST Backend
 */
app.factory('CourseFactory', function ($resource) {
    return $resource('api/courses/:id',
        {
            id: '@id',
            famous: 'false'
        },
        {update: {method: 'PUT'}}
    );
});
app.factory('UserFactory', function ($resource) {
    return $resource('api/users/:id',
        {id: '@id'},
        {update: {method: 'PATCh'}}
    );
});
app.factory('auth', function ($rootScope, $modal, $cookies,
                              UtilsFactory, $httpParamSerializer, $http, $location) {

    enter = function () {
        if ($location.path() != auth.loginPath) {
            auth.frompath = $location.path();
            if (!$rootScope.authenticated) {
                // $location.path(auth.loginPath);
            }
        }
    }

    var auth = {
        loginPath: '/login',
        logoutPath: '/logout',
        homePath: '/',
        frompath: $location.path(),

        authenticate: function (credentials, callback) {

            var req = {
                method: 'POST',
                url: "/oauth/token",
                headers: {
                    "Authorization": "Basic " + btoa("paintmooc:secret"),
                    "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
                },
                data: $httpParamSerializer(credentials)
            }

            $http(req).then(function (response) {
                console.log(response.data);
                if (response.data.access_token) {
                    $rootScope.authenticated = true;
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.access_token;
                    $cookies.put("access_token", response.data.access_token);
                    console.log($cookies.get("access_token"));
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback($rootScope.authenticated, '请求token成功');
                $location.path(auth.frompath == auth.loginPath ? auth.homePath : auth.frompath);
            }, function (response) {
                $rootScope.addError(response);
                auth.clear();
                callback && callback(false, response.data.error_description);
            });
        },

        clear: function () {
            $location.path(auth.loginPath);
            $rootScope.authenticated = false;
            $http.defaults.headers.common.Authorization = {};
            $cookies.remove("access_token");
        },

        init: function (homePath, loginPath, logoutPath) {

            auth.homePath = homePath;
            auth.loginPath = loginPath;
            auth.logoutPath = logoutPath;

            var token = $cookies.get("access_token");
            if (token) {
                $rootScope.authenticated = true;
                $http.defaults.headers.common.Authorization = 'Bearer ' + token;
            } else {
                $rootScope.authenticated = false;
            }

            // Guard route changes and switch to login page if unauthenticated
            $rootScope.$on('$routeChangeStart', function () {
                enter();
            });
        },

        showLoginForm: function (show) {
            if (show) {
                $rootScope.modalProcessing = $modal.open({
                    animation: true,
                    templateUrl: "views/modal-login.html",
                    controller: 'NavigationCtrl',
                    size: 100,
                    backdrop: 'static'
                });
                $rootScope.modalProcessing.result.then(
                    function () {
                    },
                    function () {
                        UtilsFactory.clearBackendException();
                    })
            } else {
                $rootScope.modalProcessing.close();
            }
        }

    };

    return auth;
});

app.factory('UtilsFactory', function ($rootScope) {
    var factory = {};

    $rootScope.alerts = [];

    factory.addAlert = function (type, msg) {
        $rootScope.alerts.push({"type": type, "msg": msg});
    }

    factory.closeAlert = function (index) {
        $rootScope.alerts.splice(index, 1);
    }

    $rootScope.backendException = null;

    factory.addBackendException = function (error) {
        $rootScope.backendException = error;
    }

    factory.clearBackendException = function () {
        $rootScope.backendException = null;
    }

    return factory;
});