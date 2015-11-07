'use strict';

angular.module('admApp')
    .controller('EvaluationController', function ($scope, Evaluation, EvaluationSearch, ParseLinks) {
        $scope.evaluations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Evaluation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.evaluations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Evaluation.get({id: id}, function(result) {
                $scope.evaluation = result;
                $('#deleteEvaluationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Evaluation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEvaluationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EvaluationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.evaluations = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.evaluation = {
                name: null,
                description: null,
                points: null,
                descriptionPoints: null,
                shippingPoints: null,
                shippingPricePoints: null,
                id: null
            };
        };
    });
