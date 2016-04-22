(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .directive('fileUpload', fileUpload)
        .controller('FileUploadController', FileUploadController);

    /** @ngInject */
    function fileUpload() {
        var directive = {
            restrict: 'E',
            scope: {
                extraValues: '='
            },
            templateUrl: 'app/components/fileUpload/templateToRenderModal.html',
            controller: FileUploadController,
            controllerAs: 'vm'
        };

        return directive;



    }
    /** @ngInject */
    function FileUploadController(Upload, fileUploadModal, $http) {
        var vm = this;
        vm.progressPercentage = 0;
        vm.fileUploadSuccess = false;
        vm.fileUploadError = false;
        vm.selectedCourse = null;
        vm.courseDesc = "";


        vm.courses = [];

        vm.tags = [];

        $http.get("/rest/API/courses")
            .then(function(resp) {
                _.each(resp.data, function(course) {
                    vm.courses.push(course);
                });
            });

        vm.upload = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    if (!file.$error) {
                        vm.fileUploadSuccess = false;
                        vm.fileUploadError = false;
                        Upload.upload({
                            url: '/rest/API/fileUpload/' + vm.selectedCourse.id,
                            data: {"file": file, "tags": JSON.stringify(vm.tags),
                                "courseCode": vm.selectedCourse.courseCode,
                                "courseDesc": vm.courseDesc
                            }
                        })
                            .success(function() {
                                vm.fileUploadSuccess = true;
                            })
                            .error(function() {
                                vm.fileUploadError = true;
                            })
                    }
                }
            }
        };

        vm.openModal = function() {
            fileUploadModal.openModal();
        };
    }

})();
