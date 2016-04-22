(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .service('modalFileViewer', modalFileViewer);

    /** @ngInject */
    function modalFileViewer($rootScope, $uibModal) {
        var vm = this;

        this.openModal = openModal;

        vm.getFileURI = function (fileName, filePath) {
            var folder = filePath.split("/")[1];
            //TODO make sure this port is changed to 8080
            var URIprefix = 'http://localhost:8081/static/files/' + folder + "/";
            return URIprefix + fileName;
        };


        function openModal (file, files) {
            var filePath = vm.getFileURI(file.fileName, file.filePath);


            var scope = $rootScope.$new();
            scope.params = {filePath: filePath, fileId: file.id, courseId: file.courseId, fileName: file.fileName, files: files};
            var modalInstance = $uibModal.open({
                scope: scope,
                animation: true,
                templateUrl: 'app/components/modalFileViewer/modalContent.html',
                controller: 'ModalFileViewerController',
                size: 'xl',
                windowClass: 'fileViewer'
            });
            modalInstance.updateFilesArray = function(files) {
                $rootScope.$broadcast("FILES_CHANGED", files);
            };
            modalInstance.updateFile = function (file) {
                $rootScope.$broadcast("FILE_CHANGED", file);
            };
            modalInstance.removeFileNotification = function (fileId) {
                $rootScope.$broadcast("NOTIFICATION_UPDATE", fileId);
            };
            scope.params.modalInstance = modalInstance;
        }
    }
})();
