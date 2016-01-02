'use strict';

angular.module('admApp')
    .controller('PriceController', function ($scope, Price, PriceSearch, ParseLinks) {
        $scope.prices = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Price.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.prices = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Price.get({id: id}, function(result) {
                $scope.price = result;
                $('#deletePriceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Price.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePriceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PriceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prices = result;
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
            $scope.price = {
                suggestedRetailPrice: null,
                extraShippingSuffix: null,
                discount: null,
                price: null,
                id: null
            };
        };
    });
