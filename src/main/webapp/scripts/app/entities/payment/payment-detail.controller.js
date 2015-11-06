'use strict';

angular.module('admApp')
    .controller('PaymentDetailController', function ($scope, $rootScope, $stateParams, entity, Payment) {
        $scope.payment = entity;
        $scope.load = function (id) {
            Payment.get({id: id}, function(result) {
                $scope.payment = result;
            });
        };
        var unsubscribe = $rootScope.$on('admApp:paymentUpdate', function(event, result) {
            $scope.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
