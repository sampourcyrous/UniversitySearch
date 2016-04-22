/**
 * Created by syedaarafasulaiman on 2016-03-02.
 */
(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('FollowingController', FollowingController);
    /** @ngInject */
    function FollowingController($http, $location) {
        var vm = this;
        vm.courses = [];

        $http.get('rest/API/following')
            .then(function (response) {
                vm.courses = response.data;
            });

        vm.loadCourse = function(course) {
            $location.path('/course/' + course.id);
        };
    }
})();
