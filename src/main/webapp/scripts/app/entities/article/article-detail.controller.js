'use strict';

angular.module('admApp')
    .controller('ArticleDetailController', function ($scope, $rootScope, $stateParams, entity, Article, Price) {
        $scope.article = entity;
        $scope.load = function (id) {
            Article.get({id: id}, function(result) {
                $scope.article = result;
            });
        };
        var unsubscribe = $rootScope.$on('admApp:articleUpdate', function(event, result) {
            $scope.article = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
