'use strict';

angular.module('admApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


