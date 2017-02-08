/**
 * 是否发布下拉框
 *
 * @author zhijund
 * @since 2015-09-24
 */
var DefBackRuleCtrl = ['$scope', '$http', function ($scope, $http) {

    $scope.processBackRuleChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

}];