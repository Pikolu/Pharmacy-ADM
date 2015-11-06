'use strict';

angular.module('admApp').controller('PharmacyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Pharmacy', 'Payment',
        function($scope, $stateParams, $modalInstance, entity, Pharmacy, Payment) {

        $scope.pharmacy = entity;
        $scope.payments = Payment.query();
        $scope.load = function(id) {
            Pharmacy.get({id : id}, function(result) {
                $scope.pharmacy = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('admApp:pharmacyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.pharmacy.id != null) {
                Pharmacy.update($scope.pharmacy, onSaveFinished);
            } else {
                Pharmacy.save($scope.pharmacy, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
