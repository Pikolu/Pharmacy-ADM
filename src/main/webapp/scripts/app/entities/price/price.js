'use strict';

angular.module('admApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('price', {
                parent: 'entity',
                url: '/prices',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'admApp.price.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/price/prices.html',
                        controller: 'PriceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('price');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('price.detail', {
                parent: 'entity',
                url: '/price/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'admApp.price.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/price/price-detail.html',
                        controller: 'PriceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('price');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Price', function($stateParams, Price) {
                        return Price.get({id : $stateParams.id});
                    }]
                }
            })
            .state('price.new', {
                parent: 'price',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/price/price-dialog.html',
                        controller: 'PriceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    suggestedRetailPrice: null,
                                    extraShippingSuffix: null,
                                    discount: null,
                                    price: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('price', null, { reload: true });
                    }, function() {
                        $state.go('price');
                    })
                }]
            })
            .state('price.edit', {
                parent: 'price',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/price/price-dialog.html',
                        controller: 'PriceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Price', function(Price) {
                                return Price.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('price', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
