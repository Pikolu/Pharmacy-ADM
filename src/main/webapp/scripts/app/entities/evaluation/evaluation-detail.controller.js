'use strict';

angular.module('admApp')
    .controller('EvaluationDetailController', function ($scope, $rootScope, $stateParams, entity, Evaluation, Pharmacy) {
        $scope.evaluation = entity;
        $scope.load = function (id) {
            Evaluation.get({id: id}, function(result) {
                $scope.evaluation = result;
            });
        };
        var unsubscribe = $rootScope.$on('admApp:evaluationUpdate', function(event, result) {
            $scope.evaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
