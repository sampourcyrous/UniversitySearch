(function () {
    'use strict';

    angular
        .module('angularWebApp')
        .directive('searchBar', searchBar);

    /** @ngInject */
    function searchBar() {
        var directive = {
            restrict: 'E',
            scope: {
                extraValues: '='
            },
            templateUrl: 'app/components/searchBar/searchBar.html',
            controller: SearchBarController,
            controllerAs: 'sbc'
        };

        return directive;

        /** @ngInject */
        function SearchBarController($http, $log, $location, modalFileViewer) {
            var vm = this;
            vm.selectedTerm = null;
            vm.search = search;
            vm.onSelect = onSelect;
            vm.showGlyphiconNoResults = showGlyphiconNoResults;
            vm.loadAdvancedSearch = loadAdvancedSearch;
            vm.open = open;

            function search(val) {
                var url = "rest/API/search/" + val;
                return $http.get(url)
                    .then(function (response) {
                        var returnArr = [];

                        if (_.size(response.data.courses) > 0) {
                            returnArr.push({"course_divider": true});
                            _.each(response.data.courses, function (course) {
                                var courseCodeBolded = course.courseCode.replace(vm.selectedTerm.toUpperCase(), "<b><mark>" + vm.selectedTerm.toUpperCase() + "</mark></b>");
                                course.courseCode = courseCodeBolded;
                                return returnArr.push(course);
                            })
                        }

                        if (_.size(response.data.files) > 0) {
                            returnArr.push({"file_divider": true});
                            _.each(response.data.files, function (file) {
                                return returnArr.push(file);
                            })
                        }
                        return returnArr;
                    });
            }

            function onSelect(item, model, label) {
                if (item.course_divider || item.file_divider) {
                    vm.selectedTerm = "";
                    return;
                }
                if (item.courseCode) {
                    $location.path("/course/" + item.id);
                    vm.selectedTerm = "";
                } else if (item.fileName) {
                    modalFileViewer.openModal(item);
                    vm.selectedTerm = "";
                }
            }

            vm.showNoResults = function(noResults) {
                if (noResults && !(vm.selectedTerm === "")) {
                    return "No results found!";
                } else if (vm.selectedTerm === "") {
                    return "";
                }
            };


            function showGlyphiconNoResults(noResults, input) {

                if (input == null || (input.toString().length == 0)) {
                    return false;
                }
                else return noResults;
            }

            function loadAdvancedSearch() {
                $location.path("advancedSearch");
            }
        }
    }
})();
