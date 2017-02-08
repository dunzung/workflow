/**
 * 允许委派
 *
 * @author zhijund
 * @since 2015-09-24
 */
var DefPermitDelegateCtrl = ['$scope','$http', function ($scope,$http) {

    $scope.processPermitDelegateChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

}];