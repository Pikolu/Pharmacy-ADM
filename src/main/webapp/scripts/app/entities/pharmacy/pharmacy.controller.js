'use strict';

angular.module('admApp')
    .controller('PharmacyController', function ($scope, Pharmacy, PharmacySearch, ParseLinks) {
        $scope.pharmacys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Pharmacy.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pharmacys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Pharmacy.get({id: id}, function(result) {
                $scope.pharmacy = result;
                $('#deletePharmacyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Pharmacy.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePharmacyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PharmacySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pharmacys = result;
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
            $scope.pharmacy = {
                name: null,
                shipping: null,
                logoURL: null,
                totalEvaluationPoints: null,
                id: null
            };
        };
    });
