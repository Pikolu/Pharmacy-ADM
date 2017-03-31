'use strict';

angular.module('admApp')
    .controller('ArticleDialogController', ['$scope', '$stateParams', '$modalInstance', 'entity', 'Article', 'Price',
        function ($scope, $stateParams, $modalInstance, entity, Article, Price) {

            $scope.article = entity;
            $scope.prices = Price.query();
            $scope.optionsList = Article.query();
            $scope.load = function (id) {
                Article.get({id: id}, function (result) {
                    $scope.article = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('admApp:articleUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.article.id != null) {
                    Article.update($scope.article, onSaveFinished);
                } else {
                    Article.save($scope.article, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };

            $scope.tinymceOptions = {
                plugins: 'link image code',
                toolbar: 'undo redo | bold italic | alignleft aligncenter alignright | code'
            };

            $scope.refreshProducts = function (product) {
                optionsList = ArticleSearch.query(product);
            }
        }]);
