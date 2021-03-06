'use strict';

describe('Payment Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPayment, MockPharmacy;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPayment = jasmine.createSpy('MockPayment');
        MockPharmacy = jasmine.createSpy('MockPharmacy');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Payment': MockPayment,
            'Pharmacy': MockPharmacy
        };
        createController = function() {
            $injector.get('$controller')("PaymentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'admApp:paymentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
