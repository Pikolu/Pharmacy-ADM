'use strict';

describe('Article Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockArticle, MockPrice;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockArticle = jasmine.createSpy('MockArticle');
        MockPrice = jasmine.createSpy('MockPrice');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Article': MockArticle,
            'Price': MockPrice
        };
        createController = function() {
            $injector.get('$controller')("ArticleDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'admApp:articleUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
