'use strict';

angular.module('admApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payment', {
                parent: 'entity',
                url: '/payments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'admApp.payment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payment/payments.html',
                        controller: 'PaymentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('payment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('payment.detail', {
                parent: 'entity',
                url: '/payment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'admApp.payment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payment/payment-detail.html',
                        controller: 'PaymentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('payment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Payment', function($stateParams, Payment) {
                        return Payment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('payment.new', {
                parent: 'payment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payment/payment-dialog.html',
                        controller: 'PaymentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('payment', null, { reload: true });
                    }, function() {
                        $state.go('payment');
                    })
                }]
            })
            .state('payment.edit', {
                parent: 'payment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payment/payment-dialog.html',
                        controller: 'PaymentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Payment', function(Payment) {
                                return Payment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('payment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
