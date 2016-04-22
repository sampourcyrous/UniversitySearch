(function() {
  'use strict';
  'use strict';

  angular
    .module('angularWebApp')
    .controller('ActivateController', ActivateController);

  /** @ngInject */

  function ActivateController($scope, $http, $routeParams) {
      var email = $routeParams.email;
      var hash = $routeParams.hash;

      var url = 'rest/API/activate/email/' + email + '/hash/' + hash;

      $http.get(url)
              .success(function(data, status, headers, config) {
                $scope.activateResponse = data;
                })
                .error(function(data, status, headers, config) {
                alert("AJAX failed to get data, status=" + status);
              });
    }

})();
