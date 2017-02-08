/**
 * 流程定义模型之任务会签
 *
 * @author zhijund
 * @since 2015-10-14
 */
'use strict';
var modeler = angular.module('activitiModeler');
modeler.service("SignService", ["$rootScope", function ($rootScope) {

    var service = {
        permit: false,
        hasInitial: false,
        typeInParallel: "Parallel",
        multiinstanceType: "oryx-multiinstance_type",
        changeSignType: function (signType) {
            service.hasInitial = true;
            service.permit = (signType == service.typeInParallel)
            $rootScope.$broadcast("handleChangeSignTypeBroadcast");
        },
        /**
         * 是否会签
         */
        hasParallel: function (properties) {
            for (var i = 0; i < properties.length; i++) {
                var item = properties[i];
                if (item.key == service.multiinstanceType && item.value == service.typeInParallel) {
                    return true;
                }
            }
            return false;
        }
    }

    return service;
}]);

/**
 * 会签类型
 * @author zhijund
 * @since 2015-09-24
 */
var DefSignTypeCtrl = ['$scope', '$http', 'SignService', function ($scope, $http, service) {

    $scope.processSignTypeChanged = function () {
        service.changeSignType($scope.property.value);
        if ($scope.property.value != null && $scope.property.value != "") {
            $scope.updatePropertyInModel($scope.property);
        }
    };

    if (service.hasInitial == false) {
        service.changeSignType($scope.property.value);
    }

}]

/**
 * 会签人数
 *
 * @author zhijund
 * @since 2015-09-24
 */
var DefSignAssigneesCtrl = ['$scope', '$http', 'SignService', function ($scope, $http, service) {

    $scope.$on("handleChangeSignTypeBroadcast", function () {
        $scope.property.value = (service.permit ? "${_wfl_sign_assignees}" : "");
        $scope.updatePropertyInModel($scope.property);
    })

}]

/**
 * 会签参数
 *
 * @author zhijund
 * @since 2015-09-24
 */
var DefSignVariableCtrl = ['$scope', '$http', 'SignService', function ($scope, $http, service) {

    $scope.$on("handleChangeSignTypeBroadcast", function () {
        $scope.property.value = (service.permit ? "_wfl_sign_variable" : "");
        $scope.updatePropertyInModel($scope.property);
    })

}]

/**
 * 会签完成条件
 *
 * @author zhijund
 * @since 2015-09-24
 */
var DefSignCompletedConditionCtrl = ['$scope', '$http', 'SignService', function ($scope, $http, service) {

    $scope.$on("handleChangeSignTypeBroadcast", function () {
        $scope.property.value = (service.permit ? "${_wfl_sign_completed_condition == true}" : "");
        $scope.updatePropertyInModel($scope.property);
    })

}]

/**
 * @type {*[]}
 */
var DefVoteTypeCtrl = ['$scope', '$http', 'SignService', function ($scope, $http, service) {
    $scope.VoteType = {
        disable: false
    }

    $scope.VoteType.disable = service.hasParallel($scope.selectedItem.properties);
    if ($scope.VoteType.disable == false) {
        $scope.property.value = "";
        $scope.updatePropertyInModel($scope.property);
    } else {
        // 默认是一票否决
        if ($scope.property.value == "") {
            $scope.property.value = "0";
            $scope.updatePropertyInModel($scope.property);
        }
    }

    $scope.processVoteTypeChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

    $scope.$on("handleChangeSignTypeBroadcast", function () {
        $scope.VoteType.disable = service.permit;
        if ($scope.VoteType.disable == false) {
            $scope.property.value = "";
            $scope.updatePropertyInModel($scope.property);
        }
    })

}]

/**
 * 回退规则
 * @type {*[]}
 */
var DefBackRuleCtrl = ['$scope', '$http', 'SignService', function ($scope, $http, service) {

    $scope.BackRule = {
        disable: false
    }

    $scope.BackRule.disable = service.hasParallel($scope.selectedItem.properties);
    if ($scope.BackRule.disable == true) {
        $scope.property.value = "";
    } else {
        if ($scope.property.value == "") {
            $scope.property.value = "0";
        }
    }

    $scope.updatePropertyInModel($scope.property);

    $scope.processBackRuleChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

    $scope.$on("handleChangeSignTypeBroadcast", function () {
        $scope.BackRule.disable = service.permit;
        if ($scope.BackRule.disable == true) {
            $scope.property.value = "";
            $scope.updatePropertyInModel($scope.property);
        }
    })
}];
modeler.controller("activitiModeler", DefBackRuleCtrl);
modeler.controller("activitiModeler", DefVoteTypeCtrl);
modeler.controller("activitiModeler", DefSignTypeCtrl);
modeler.controller("activitiModeler", DefSignVariableCtrl)
modeler.controller("activitiModeler", DefSignAssigneesCtrl);
modeler.controller("activitiModeler", DefSignCompletedConditionCtrl)