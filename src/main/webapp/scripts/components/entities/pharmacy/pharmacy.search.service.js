'use strict';

angular.module('admApp')
    .factory('PharmacySearch', function ($resource) {
        return $resource('api/_search/pharmacys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
