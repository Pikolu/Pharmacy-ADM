'use strict';

angular.module('admApp')
    .factory('EvaluationSearch', function ($resource) {
        return $resource('api/_search/evaluations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
