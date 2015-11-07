'use strict';

angular.module('admApp').controller('PharmacyDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Pharmacy', 'Payment', 'User', 'Evaluation',
        function($scope, $stateParams, $modalInstance, $q, entity, Pharmacy, Payment, User, Evaluation) {

        $scope.pharmacy = entity;
        $scope.payments = Payment.query();
        $scope.users = User.query();
        $scope.evaluations = Evaluation.query();
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
