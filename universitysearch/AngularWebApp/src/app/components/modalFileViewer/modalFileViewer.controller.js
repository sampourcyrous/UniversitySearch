(function () {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('ModalFileViewerController', ModalFileViewerController);
    /** @ngInject */

    function ModalFileViewerController($scope, $uibModalInstance, $http, Upload, $sce, $cookies) {
        var vm = $scope;
        vm.tags = [];
        vm.oldTags = [];
        vm.isProf = $cookies.getObject("globals").currentUser.isProf;
        vm.approved = false;
        vm.approvedText = "Approve File";

        vm.getTags = function () {
            $http({
                    method: "GET",
                    url: '/rest/API/getTags/' + vm.params.fileId
                })
                .success(function(data) {
                    _.each(data, function(tag) {
                        vm.oldTags.push((tag));
                        vm.tags.push((tag));
                    });
                })
                .error(function() {

                })
        };

        vm.submitTags = function () {
            vm.newTags = [];
            var lenOld = _.allKeys(vm.oldTags);
            var lenNew = _.allKeys(vm.tags);

            var i,j;
            var found = false;
            for ( i=0; i < lenNew.length; i++){
                found = false;
                for (j=0; j<lenOld.length; j++) {
                    if (vm.tags[i].text == vm.oldTags[j].text) {
                        found = true;
                    }
                }
                if (!found) {
                    vm.newTags.push(vm.tags[i]);
                }
            }

            Upload.upload({
                    url: '/rest/API/addTags/' + vm.params.fileId,
                    data: {tags: JSON.stringify(vm.newTags)}
                })
                .success(function() {
                    vm.tagSuccess = true;

                })
                .error(function() {
                    vm.fileUploadError = true;
                })
        };

        vm.ok = function () {
            $uibModalInstance.close();
        };

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        };

        vm.deleteFile = function () {

            $http.post("/rest/API/deleteFile/" + vm.params.fileId)
                .then (function() {
                    vm.params.files = _.reject(vm.params.files, function (file) {
                        return file.id == vm.params.fileId;
                    });
                    vm.params.modalInstance.updateFilesArray(vm.params.files);
                    vm.params.modalInstance.close();
                })
        };

        vm.approveFile = function(fileId) {
            $http.post("/rest/API/approve/" + vm.params.fileId)
                .then(function() {
                    vm.approved = true;
                    vm.approvedText = "File Approved";
                    vm.params.modalInstance.updateFile(vm.params.fileId);
                }, function(resp) {
                    if (resp.status == 401) {
                        vm.showApprovalError = true;
                    }
                })
        };

        $http.get("/rest/API/isApproved/" + vm.params.fileId)
            .then(function(resp) {
                if (resp.data.isApproved == 1) {
                    vm.approved = true;
                    vm.approvedText = "File Approved";
                }
            });


        $http.post("/rest/API/removeNotification/" + vm.params.fileId)
            .then (function (resp) {
                vm.params.modalInstance.removeFileNotification(vm.params.fileId);
            });


        vm.getTags();
    }
})();
