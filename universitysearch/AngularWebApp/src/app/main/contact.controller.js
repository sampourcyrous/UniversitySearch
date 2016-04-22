/**
 * Created by syedaarafasulaiman on 2016-03-02.
 */
(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .controller('ContactController', ContactController);
  /** @ngInject */
  function ContactController($timeout, webDevTec, toastr) {
    var vm = this;

    vm.awesomeThings = [];
    vm.classAnimation = '';
    vm.creationDate = 1456267200629;
    vm.showToastr = showToastr;

    vm.users=[{username:'sulaim11',password:'123'},
      {username:'user',password:'p'}];
    vm.loggedin = [];

    vm.orderProp = 'age';

    activate();

    function activate() {
      getWebDevTec();
      $timeout(function () {
        vm.classAnimation = 'rubberBand';
      }, 4000);
    }

    function showToastr() {
      toastr.info('Fork <a href="https://github.com/Swiip/generator-gulp-angular" target="_blank"><b>generator-gulp-angular</b></a>');
      vm.classAnimation = '';

    }

    function getWebDevTec() {
      vm.awesomeThings = webDevTec.getTec();

      angular.forEach(vm.awesomeThings, function (awesomeThing) {
        awesomeThing.rank = Math.random();
      });
    }




  }
})();
