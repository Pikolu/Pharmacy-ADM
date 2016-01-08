'use strict';

angular.module('admApp')
    .controller('PriceDetailController', function ($scope, $rootScope, $stateParams, entity, Price, Pharmacy, Article) {
        $scope.price = entity;
        $scope.load = function (id) {
            Price.get({id: id}, function(result) {
                $scope.price = result;
            });
        };
        var unsubscribe = $rootScope.$on('admApp:priceUpdate', function(event, result) {
            $scope.price = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
