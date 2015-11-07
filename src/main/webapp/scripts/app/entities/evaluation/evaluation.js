'use strict';

angular.module('admApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('evaluation', {
                parent: 'entity',
                url: '/evaluations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'admApp.evaluation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/evaluation/evaluations.html',
                        controller: 'EvaluationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evaluation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('evaluation.detail', {
                parent: 'entity',
                url: '/evaluation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'admApp.evaluation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/evaluation/evaluation-detail.html',
                        controller: 'EvaluationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evaluation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Evaluation', function($stateParams, Evaluation) {
                        return Evaluation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('evaluation.new', {
                parent: 'evaluation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/evaluation/evaluation-dialog.html',
                        controller: 'EvaluationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    points: null,
                                    descriptionPoints: null,
                                    shippingPoints: null,
                                    shippingPricePoints: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('evaluation', null, { reload: true });
                    }, function() {
                        $state.go('evaluation');
                    })
                }]
            })
            .state('evaluation.edit', {
                parent: 'evaluation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/evaluation/evaluation-dialog.html',
                        controller: 'EvaluationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Evaluation', function(Evaluation) {
                                return Evaluation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('evaluation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
