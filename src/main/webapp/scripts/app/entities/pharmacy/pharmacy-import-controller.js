/**
 * Created by Alexander on 25.02.2016.
 */
'use strict';

angular.module('admApp').controller('MyCtrl', ['$scope', 'Upload', function ($scope, Upload) {
    // upload later on form submit or something similar
    $scope.submitUploadFile = function() {
        if ($scope.editForm.file.$valid && $scope.file) {
            $scope.upload($scope.file);
        }
    };

    // upload on file select or drop
    $scope.upload = function (file) {
        Upload.upload({
            url: 'api/upload/url',
            fields: {'username': 'zouroto'}, // additional data to send
            file: file
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };

    //Upload.upload({
    //    url: 'api/upload/url',
    //    fields: {'username': 'zouroto'}, // additional data to send
    //    file: file
    //}).progress(function (evt) {
    //    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
    //    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
    //}).success(function (data, status, headers, config) {
    //    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
    //});
}]);
