/**
 * Created by syedaarafasulaiman on 2016-03-25.
 */

(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .controller('fileManagementController', fileManagementController);
  /** @ngInject */
  function fileManagementController($http, $sce) {
    var vm = this;
    vm.hardFiles = [{name: "M.txt"},
      {name: "T.pdf"},
      {name: "J.html"}];
    vm.gridOptions = { data: 'hardFiles' };


  }
})();
