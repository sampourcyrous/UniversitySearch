/**
 * Created by syedaarafasulaiman on 2016-03-02.
 */
(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('CourseController', CourseController);
    /** @ngInject */
    function CourseController($http, $routeParams, $location) {
        var vm = this;
        vm.courseId = $routeParams.id;
        vm.course = {};
        vm.files = [];
        vm.following = false;
        vm.followText = "";
        vm.hover = false;

        $http.get("/rest/API/course/" + vm.courseId)
            .then(function (response) {
                vm.course = response.data;
                $http.get("/rest/API/filesForCourse/" + vm.courseId)
                    .then(function (response) {
                        if (_.size(response.data) > 0) {
                            _.each(response.data, function (file) {
                                return vm.files.push(file);
                            })
                        }
                        $http.get("/rest/API/following")
                            .then(function (resp) {
                                _.each(resp.data, function (course) {
                                    if (course.id == vm.courseId) {
                                        vm.following = true;
                                        vm.followText = "Following";
                                    }
                                });
                                if (!vm.following) {
                                    vm.followText = "Follow";
                                }
                            })
                    });
            }, function (err) {
                $location.path('/');
            });

        vm.toggleFollowing = function (){
            if (vm.following) {
                $http.post("/rest/API/unFollowCourse/" + vm.courseId)
                    .then(function () {
                        vm.following = false;
                        vm.followText = "Follow";
                    })
            } else {
                $http.post("/rest/API/followCourse/" + vm.courseId)
                    .then (function () {
                        vm.following = true;
                        vm.followText = "Following";
                    })
            }
        };
    }
})();
