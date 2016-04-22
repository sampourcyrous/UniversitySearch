/**
 * Created by syedaarafasulaiman on 2016-03-02.
 */
(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('AddDeleteCourseController', AddDeleteCourseController);
    /** @ngInject */
    function AddDeleteCourseController($http, $scope) {
        var vm = this;
        vm.courses = [];
        vm.selectedCourse ="";
        vm.showAddCourseSuccess = false;
        vm.showDeleteCourseSuccess = false;

        $http.get('/rest/API/coursesForProf')
            .then(function (resp) {
                _.each(resp.data, function(course) {
                    vm.courses.push(course);
                });
            });

        vm.addCourse = function(course) {
            course.courseCode = course.courseCode.toUpperCase();
            return $http.post('rest/API/addCourse', course)
                .then(function (resp) {
                    vm.showAddCourseSuccess = true;
                    if (_.findWhere(vm.courses, {courseCode: resp.data.courseCode}) == null) {
                        vm.courses.push(resp.data);
                    }
                    $scope.course.courseDesc = "";
                    $scope.course.courseName = "";
                    $scope.course.courseCode = "";
                });
        };

        vm.deleteCourse = function() {
            return $http.post('rest/API/deleteCourse/' + vm.selectedCourse.id)
                .then(function (resp) {
                    vm.courses = _.reject(vm.courses, function (course) {
                        return (course.id == vm.selectedCourse.id);
                    });
                    vm.showDeleteCourseSuccess = true;
                    vm.selectedCourse = "";
                });
        };

    }
})();
