(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .service('fileUploadModal', fileUploadModal);

    /** @ngInject */
    function fileUploadModal($rootScope, $uibModal) {

        this.openModal = openModal;

        function openModal () {

            var scope = $rootScope.$new();
            scope.params = {};
            var modalInstance = $uibModal.open({
                scope: scope,
                animation: true,
                templateUrl: 'app/components/fileUpload/fileUpload.html',
                controller: 'FileUploadController',
                size: 'md',
                windowClass: 'fileUpload'
            });
        }
    }
})();
