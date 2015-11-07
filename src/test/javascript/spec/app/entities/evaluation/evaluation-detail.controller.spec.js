'use strict';

describe('Evaluation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEvaluation, MockPharmacy;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEvaluation = jasmine.createSpy('MockEvaluation');
        MockPharmacy = jasmine.createSpy('MockPharmacy');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Evaluation': MockEvaluation,
            'Pharmacy': MockPharmacy
        };
        createController = function() {
            $injector.get('$controller')("EvaluationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'admApp:evaluationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
