'use strict';

angular.module('admApp')
    .controller('ArticleController', function ($scope, Article, ArticleSearch, ParseLinks) {
        $scope.articles = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Article.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.articles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Article.get({id: id}, function(result) {
                $scope.article = result;
                $('#deleteArticleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Article.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteArticleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ArticleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.articles = result;
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
            $scope.article = {
                name: null,
                description: null,
                articelNumber: null,
                imageURL: null,
                deepLink: null,
                keyWords: null,
                id: null
            };
        };
    });
