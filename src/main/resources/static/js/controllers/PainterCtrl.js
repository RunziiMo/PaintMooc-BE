var app = angular.module('paintmooc.controllers');

app.controller('PainterCtrl', function ($scope, $rootScope, $location, UserFactory, UtilsFactory) {

    $scope.num = 6;

    $scope.listAll = function (num) {
        $rootScope.showProcessing();
        UserFactory.query(
            {num: num}
            , function (response) {
                console.log(response);
                $rootScope.closeProcessing();
                $scope.painters = response ? response : [];
            },
            function (error) {
                $rootScope.addError(error);
            }
        );
    }

    $scope.listAll($scope.num);

    $scope.editCar = function (carId) {
        $location.path('/cars-edit/' + carId);
    }

    $scope.savePainter = function (painter) {
        $rootScope.showProcessing();
        if (painter.id) {
            painter.$update(function (response) {
                $rootScope.closeProcessing();
                painter.$edit = false;
                UtilsFactory.addAlert('成功', '用户更改已保存');
            }, function (error) {
                $rootScope.addError(error);
            });
        } else {
            painter.password = '123456';
            UserFactory.save(painter, function () {
                    painter.$edit = false;
                    $rootScope.closeProcessing();
                    UtilsFactory.addAlert('成功', '用户' + painter.username + '已被添加');
                },
                function (error) {
                    $rootScope.addError(error);
                }
            );
        }
    }

    $scope.insertPainter = function () {
        var newpainter = {
            courses: 0,
            fans: 0,
            likes: 0,
            follows: 0
        }
        $scope.painters.push(newpainter);
        newpainter.$edit = true;
        // $location.path('/cars-insert');
    }
    $scope.deletePainter = function (painter) {
        if (painter.id) {
            $rootScope.showProcessing();
            UserFactory.delete({id: painter.id},
                function () {
                    $rootScope.closeProcessing();
                    UtilsFactory.addAlert('成功', '用户' + painter.username + '已被删除');
                    $scope.listAll(6);
                },
                function (error) {
                    $rootScope.addError(error);
                }
            );
        }else {
            $scope.listAll(6);
        }
    }
});

