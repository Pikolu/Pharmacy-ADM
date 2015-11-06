'use strict';

angular.module('admApp')
    .controller('PaymentController', function ($scope, Payment, PaymentSearch, ParseLinks) {
        $scope.payments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Payment.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.payments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Payment.get({id: id}, function(result) {
                $scope.payment = result;
                $('#deletePaymentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Payment.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePaymentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PaymentSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.payments = result;
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
            $scope.payment = {
                name: null,
                shipping: null,
                logoURL: null,
                totalEvaluationPoints: null,
                id: null
            };
        };
    });
