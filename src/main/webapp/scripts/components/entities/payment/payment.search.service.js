'use strict';

angular.module('admApp')
    .factory('PaymentSearch', function ($resource) {
        return $resource('api/_search/payments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
