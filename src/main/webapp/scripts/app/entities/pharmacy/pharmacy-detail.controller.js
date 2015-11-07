'use strict';

angular.module('admApp')
    .controller('PharmacyDetailController', function ($scope, $rootScope, $stateParams, entity, Pharmacy, Payment, User, Evaluation) {
        $scope.pharmacy = entity;
        $scope.load = function (id) {
            Pharmacy.get({id: id}, function(result) {
                $scope.pharmacy = result;
            });
        };
        var unsubscribe = $rootScope.$on('admApp:pharmacyUpdate', function(event, result) {
            $scope.pharmacy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
