/**
 * 任务定义
 *
 * @author zhijund
 * @since 2015-10-14
 */
'use strict';
var modeler = angular.module('activitiModeler');
modeler.service("TaskService", ["$rootScope", "$http", "$q", function ($rootScope, $http, $q) {
    var service = {
        startListeners: [],
        endListeners: [],
        getStartListeners: function () {
            return service.startListeners;
        },
        getEndListeners: function () {
            return service.endListeners;
        },
        initListeners: function () {
            var deferred = $q.defer();
            var config = {
                method: "GET",
                params: {
                    typein: "create,complete"
                },
                url: ACTIVITI.CONFIG.contextRoot + "/workflow/listener/task/listeners"
            }
            $http(config)
                .success(function (data, status) {
                    deferred.resolve(data);
                })
            var promise = deferred.promise;
            promise.then(function (data) {
                var defaultItem = {
                    "className": "",
                    "realName": ""
                }
                service.startListeners.push(defaultItem);
                service.endListeners.push(defaultItem);
                for (var i = 0; i < data.length; i++) {
                    var item = {
                        "className": data[i].className,
                        "realName": data[i].name
                    }
                    if (data[i].type == "create") {
                        service.startListeners.push(item);
                    }
                    if (data[i].type == "complete") {
                        service.endListeners.push(item);
                    }
                }
            })
        }
    }
    service.initListeners();
    return service;
}]);

var DefAttrIdCtrl = ['$scope', 'UUIDService', function ($scope, UUIDService) {
    $scope.AttrId = {}

    $scope.updatePropertyInModel($scope.property);

    $scope.processAttrIdChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

    if ($scope.property.value == "") {
        var stencilId = $scope.selectedShape.getStencil().idWithoutNs();
        var AttrUUID = UUIDService.genUUID(stencilId, $scope.editor.getJSON().childShapes);
        $scope.property.value = AttrUUID;
        $scope.updatePropertyInModel($scope.property);
    }
}]

var DefAttrNameCtrl = ['$scope', '$http', 'TaskService', function ($scope, $http, service) {

    $scope.processAttrNameChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

    if ($scope.property.value == "") {
        var stencilId = $scope.selectedShape.getStencil().idWithoutNs();
        if (stencilId == "StartNoneEvent"){
            $scope.property.value = "开始"
        }
        if (stencilId == "EndNoneEvent"){
            $scope.property.value = "结束"
        }
    }

    $scope.updatePropertyInModel($scope.property);

}]
/**
 * 分配人
 * @type {*[]}
 */
var DefTaskAssigneeCtrl = ['$scope', '$http', 'TaskService', function ($scope, $http, service) {
    $scope.assignment = {
        assignee: "${assignee}"
    }
    $scope.property.value = {};
    $scope.property.value.assignment = $scope.assignment;
    $scope.updatePropertyInModel($scope.property);
}]
/**
 * 开始监听器
 * @type {*[]}
 */
var DefTaskStartListenerCtrl = ['$scope', '$http', 'TaskService', function ($scope, $http, service) {
    $scope.startListeners = service.getStartListeners();

    $scope.processTaskStartListenerChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };
}]
/**
 * 结束监听器
 * @type {*[]}
 */
var DefTaskEndListenerCtrl = ['$scope', '$http', 'TaskService', function ($scope, $http, service) {

    $scope.endListeners = service.getEndListeners();

    $scope.processTaskEndListenerChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

}]

/**
 * 是否允许回退下拉框
 *
 * @author zhijund
 * @since 2015-09-24
 */
var DefBackRuleCtrl = ['$scope', '$http', 'SignService', function ($scope, $http, service) {

    $scope.backRule = {}

    $scope.processBackRuleChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

}];

/**
 * 允许委派
 *
 * @author zhijund
 * @since 2015-09-24
 */
var DefPermitDelegateCtrl = ['$scope', '$http', function ($scope, $http) {

    $scope.processPermitDelegateChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };

}];

modeler.controller("activitiModeler", DefAttrIdCtrl);
modeler.controller("activitiModeler", DefAttrNameCtrl);
modeler.controller("activitiModeler", DefBackRuleCtrl);
modeler.controller("activitiModeler", DefTaskAssigneeCtrl);
modeler.controller("activitiModeler", DefPermitDelegateCtrl);
modeler.controller("activitiModeler", DefTaskEndListenerCtrl);
modeler.controller("activitiModeler", DefTaskStartListenerCtrl);