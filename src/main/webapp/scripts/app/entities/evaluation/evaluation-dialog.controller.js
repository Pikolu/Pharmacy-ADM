'use strict';

angular.module('admApp').controller('EvaluationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Evaluation', 'Pharmacy',
        function($scope, $stateParams, $modalInstance, entity, Evaluation, Pharmacy) {

        $scope.evaluation = entity;
        $scope.pharmacys = Pharmacy.query();
        $scope.load = function(id) {
            Evaluation.get({id : id}, function(result) {
                $scope.evaluation = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('admApp:evaluationUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.evaluation.id != null) {
                Evaluation.update($scope.evaluation, onSaveFinished);
            } else {
                Evaluation.save($scope.evaluation, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
