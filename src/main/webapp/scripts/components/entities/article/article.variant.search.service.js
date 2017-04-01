/**
 * Created by Alex Tina on 01.04.2017.
 */
'use strict';

angular.module('admApp')
    .factory('ArticleVariantSearch', function ($resource) {
        return $resource('api/_search/articles/variant/:query', {}, {
            'query': { method: 'GET', isArray: true},
        });
    });
