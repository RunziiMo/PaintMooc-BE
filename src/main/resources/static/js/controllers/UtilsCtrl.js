var app = angular.module('paintmooc.controllers');

app.controller('UtilsCtrl', function ($scope, $rootScope, $modal, $location, UtilsFactory) {
    /**
     * Default message for invalid fields
     */
    $rootScope.FORM_ERROR_ALERT = 'Some fields are invalid.';
    /**
     * Close alert message (bootstrap-ui alert)
     */
    $scope.closeAlert = UtilsFactory.closeAlert;

    /**
     * Open a modal window showing "Processing..."
     */
    $rootScope.showProcessing = function () {
        $rootScope.modalProcessing = $modal.open({
            animation: true,
            templateUrl: "views/modal-processing.html",
            controller: 'UtilsCtrl',
            size: 100,
            backdrop: 'static'
        });
        $rootScope.modalProcessing.result.then(
            function () {
            },
            function () {
                UtilsFactory.clearBackendException();
            })
    };

    /**
     * Close the modal window "Processing..."
     */
    $rootScope.closeProcessing = function () {
        UtilsFactory.clearBackendException();
        $rootScope.modalProcessing.close();
    }

    /**
     * Add an error returned by the Backend.
     */
    $rootScope.addError = function (error) {
        console.log(error);
        if (error.status == 500) {
            $rootScope.closeProcessing();
            $location.path('/error');
        } else if (error.status == 400) {
            UtilsFactory.addBackendException(error);
        }
    }

});
