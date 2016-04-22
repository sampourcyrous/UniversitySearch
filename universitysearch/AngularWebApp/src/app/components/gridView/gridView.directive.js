(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .directive('gridView', gridView);

    /** @ngInject */
    function gridView() {
        var directive = {
            restrict: 'E',
            scope: {
                courses: '=',
                files: '='
            },
            templateUrl: 'app/components/gridView/gridView.html',
            controller: GridViewController,
            controllerAs: 'gvc'
        };

        return directive;

        /** @ngInject */
        function GridViewController($scope, modalFileViewer, $http, $location) {
            var vm = this;

            vm.files = $scope.files;
            vm.courses = $scope.courses;
            vm.trustSrc = function(src) {
                return $sce.trustAsResourceUrl(src);
            };

            vm.routeToCoursePage = function (id) {
                $location.path("/courses" + id);
            };

            vm.openFileModal = function (file) {
                modalFileViewer.openModal(file, $scope.files);
            };

            vm.getCourseCode = function (id) {
                vm.courseById = id;
                _.each(vm.files, function (file) {
                    if (file.courseId == vm.courseById) {
                        vm.courseCode =  file.courseCode;
                    }
                });
                return vm.courseCode;
            };

            $scope.$on("FILES_CHANGED", function (evt, files) {
                vm.files = files;
            });

            $scope.$on("FILE_CHANGED", function (evt, file) {
                vm.approvedFile = file;
                vm.files = _.each (vm.files, function (file) {

                    if (vm.approvedFile == file.id) {
                        file.isApprov = true;
                    }
                });
            });

            $scope.$watchCollection('files', function () {
                _.each (vm.files, function (file) {
                    $http.get("/rest/API/course/" + file.courseId)
                        .then (function (resp) {
                            file.courseCode = resp.data.courseCode;
                        });
                });
            })
        }
    }
})();
