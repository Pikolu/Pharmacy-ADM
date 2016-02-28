'use strict';

angular.module('admApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pharmacy', {
                parent: 'entity',
                url: '/pharmacys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'admApp.pharmacy.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pharmacy/pharmacys.html',
                        controller: 'PharmacyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pharmacy');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pharmacy.detail', {
                parent: 'entity',
                url: '/pharmacy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'admApp.pharmacy.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pharmacy/pharmacy-detail.html',
                        controller: 'PharmacyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pharmacy');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Pharmacy', function($stateParams, Pharmacy) {
                        return Pharmacy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pharmacy.new', {
                parent: 'pharmacy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pharmacy/pharmacy-dialog.html',
                        controller: 'PharmacyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    shipping: null,
                                    logoURL: null,
                                    totalEvaluationPoints: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pharmacy', null, { reload: true });
                    }, function() {
                        $state.go('pharmacy');
                    })
                }]
            })
            .state('pharmacy.edit', {
                parent: 'pharmacy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pharmacy/pharmacy-dialog.html',
                        controller: 'PharmacyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Pharmacy', function(Pharmacy) {
                                return Pharmacy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pharmacy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pharmacy.import', {
                parent: 'pharmacy',
                url: '/import',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pharmacy/pharmacy-import-dialog.html',
                        controller: 'MyCtrl',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    shipping: null,
                                    logoURL: null,
                                    totalEvaluationPoints: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                            $state.go('pharmacy', null, { reload: true });
                        }, function() {
                            $state.go('pharmacy');
                        })
                }]
            });
    });
