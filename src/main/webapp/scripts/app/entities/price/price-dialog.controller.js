'use strict';

angular.module('admApp').controller('PriceDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Price', 'Pharmacy', 'Article',
        function($scope, $stateParams, $modalInstance, $q, entity, Price, Pharmacy, Article) {

        $scope.price = entity;
        $scope.pharmacys = Pharmacy.query({filter: 'price-is-null'});
        $q.all([$scope.price.$promise, $scope.pharmacys.$promise]).then(function() {
            if (!$scope.price.pharmacy.id) {
                return $q.reject();
            }
            return Pharmacy.get({id : $scope.price.pharmacy.id}).$promise;
        }).then(function(pharmacy) {
            $scope.pharmacys.push(pharmacy);
        });
        $scope.articles = Article.query();
        $scope.load = function(id) {
            Price.get({id : id}, function(result) {
                $scope.price = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('admApp:priceUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.price.id != null) {
                Price.update($scope.price, onSaveFinished);
            } else {
                Price.save($scope.price, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
