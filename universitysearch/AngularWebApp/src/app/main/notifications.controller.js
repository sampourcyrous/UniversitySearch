(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('NotificationsController', NotificationsController);
    /** @ngInject */
    function NotificationsController($scope) {
        var vm = this;
        vm.filesUploaded = [];
        $scope.$on("NOTIFICATIONS_ARRAY", function (event, data) {
            _.each(data, function (file) {
                vm.filesUploaded.push(file);
            })
        });
    }
})();