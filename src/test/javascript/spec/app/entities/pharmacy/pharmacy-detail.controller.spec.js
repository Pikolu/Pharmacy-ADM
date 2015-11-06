'use strict';

describe('Pharmacy Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPharmacy, MockPayment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPharmacy = jasmine.createSpy('MockPharmacy');
        MockPayment = jasmine.createSpy('MockPayment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Pharmacy': MockPharmacy,
            'Payment': MockPayment
        };
        createController = function() {
            $injector.get('$controller')("PharmacyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'admApp:pharmacyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
