'use strict';

angular.module('admApp').controller('PaymentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Payment',
        function($scope, $stateParams, $modalInstance, entity, Payment) {

        $scope.payment = entity;
        $scope.load = function(id) {
            Payment.get({id : id}, function(result) {
                $scope.payment = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('admApp:paymentUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.payment.id != null) {
                Payment.update($scope.payment, onSaveFinished);
            } else {
                Payment.save($scope.payment, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
