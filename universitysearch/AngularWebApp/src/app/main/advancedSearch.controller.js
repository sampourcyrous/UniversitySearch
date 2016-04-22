/**
 * Created by syedaarafasulaiman on 2016-03-02.
 */
(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('AdvancedSearchController', AdvancedSearchController);
    /** @ngInject */
    function AdvancedSearchController($http, $location) {
        var vm = this;
        vm.advancedSearch = advancedSearch;
        vm.buildSearchQuery = buildSearchQuery;
        vm.all = "";
        vm.exact = "";
        vm.any = "";
        vm.none = "";
        vm.courses = [];
        vm.files = [];

        function advancedSearch() {
            var query = buildSearchQuery();
            var url = "rest/API/search/" + query;

            return $http.get(url)
                .then(function (response) {
                    vm.courses.length = 0;
                    vm.files.length = 0;
                    if (_.size(response.data.courses) > 0) {
                        _.each(response.data.courses, function (course) {
                            return vm.courses.push(course);
                        })
                    }

                    if (_.size(response.data.files) > 0) {
                        _.each(response.data.files, function (file) {
                            return vm.files.push(file);
                        })
                    }
                });
        }

        function buildSearchQuery() {
            return (vm.all + " " + vm.exact + " " +  vm.any + " " +  vm.none);
        }
    }
})();
