'use strict';

describe('Price Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPrice, MockPharmacy, MockArticle;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPrice = jasmine.createSpy('MockPrice');
        MockPharmacy = jasmine.createSpy('MockPharmacy');
        MockArticle = jasmine.createSpy('MockArticle');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Price': MockPrice,
            'Pharmacy': MockPharmacy,
            'Article': MockArticle
        };
        createController = function() {
            $injector.get('$controller')("PriceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'admApp:priceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
