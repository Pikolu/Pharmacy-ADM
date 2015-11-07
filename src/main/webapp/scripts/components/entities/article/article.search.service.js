'use strict';

angular.module('admApp')
    .factory('ArticleSearch', function ($resource) {
        return $resource('api/_search/articles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
