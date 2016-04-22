/**
 * Created by syedaarafasulaiman on 2016-03-24.
 */


(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .directive('sideNavbar', sideNavbar);

  /** @ngInject */
  function sideNavbar() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/vertNavbar/vertnavbar.html',
      scope: {
        creationDate: '=',
        firstName: '='
      },
      controller: sideNavbarController,
      controllerAs: 'side',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function sideNavbarController(moment,$http,$cookies,$location, $scope, $rootScope) {
      var vm = this;
      vm.notificationsNum = 0;
      // "vm.creation" is avaible by directive option "bindToController: true"
      vm.relativeDate = moment(vm.creationDate).fromNow();
      vm.user_info = $cookies.getObject('globals').currentUser.firstName;
      vm.isProf = $cookies.getObject('globals').currentUser.isProf;
      $scope.sideBarCollapsed = true;

      vm.logout =  function (){

        //var globalCookie = $cookie.get('globals');
        $http.post('/rest/API/signOut')
          .then(function () {
            $rootScope.globals = {};
            //$cookies.put('globals', $rootScope.globals);
            $cookies.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
            //$cookies.globals = $rootScope.globals;
            $location.path("/");
          });
      };

      vm.redirectToNotifications = function() {
        $location.path("notifications");
      };

      $http.get('rest/API/notifications')
          .then(function (response) {
            if (response.data.length > 0) {
              vm.notificationsNum = response.data.length;
              $scope.$emit("NOTIFICATIONS_ARRAY", response.data);
            }
          });

      $scope.$on("NOTIFICATION_UPDATE", function (event, resp) {
        vm.notificationsNum --;
      })
    }
  }

})();
