/**
 * 流程模型
 *
 * @author zhijund
 */
var modeler = angular.module('activitiModeler');
modeler.service("ModelService", ["$rootScope", "$http", "$q", function ($rootScope, $http, $q) {
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
                    typein: "start,end"
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
                    if (data[i].type == "start") {
                        service.startListeners.push(item);
                    }
                    if (data[i].type == "end") {
                        service.endListeners.push(item);
                    }
                }
            })
        }
    }
    service.initListeners();
    return service;
}]);

var DefProcessIdCtrl = ['$scope', 'ModelService', function ($scope, service) {

    var operateType = EDITOR.UTIL.getParameterByName('operateType')
    $scope.ProcessId = { // 修改
        readonly: (operateType == "2")
    }

    $scope.updatePropertyInModel($scope.property);

    $scope.processProcessidChanged = function () {
        $scope.valueFlushed = true;
        if ($scope.property.value) {
            $scope.property.value = $scope.property.value.replace(/(<([^>]+)>)/ig, "");
        }
        $scope.updatePropertyInModel($scope.property);
    };
}];

var DefProcessNameCtrl = ['$scope', 'ModelService', function ($scope, service) {
    $scope.ProcessName = {}

    $scope.updatePropertyInModel($scope.property);

    $scope.processProcessNameChanged = function () {
        $scope.valueFlushed = true;
        if ($scope.property.value) {
            $scope.property.value = $scope.property.value.replace(/(<([^>]+)>)/ig, "");
        }
        $scope.updatePropertyInModel($scope.property);
    };
}];

/**
 *  备注
 * @author zhijund
 * @since 2015-09-24
 */
var DefRemarkCtrl = ['$scope', 'ModelService', function ($scope, service) {

    $scope.updatePropertyInModel($scope.property);

    $scope.processProcessRemarkChanged = function () {
        $scope.valueFlushed = true;
        if ($scope.property.value) {
            $scope.property.value = $scope.property.value.replace(/(<([^>]+)>)/ig, "");
        }
        $scope.updatePropertyInModel($scope.property);
    };
}];

/**
 *  流转线通过条件
 * @author zhijund
 * @since 2015-09-24
 */
var DefSequenceFlowConditionCtrl = ['$scope', 'ModelService', function ($scope, service) {
    $scope.processSequenceFlowConditionChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };
}];


var DefGatewayStartListenerCtrl = ['$scope', '$http', 'ModelService', function ($scope, $http, service) {
    $scope.startListeners = service.getStartListeners();
    $scope.processGatewayStartListenerChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };
}]

var DefGatewayEndListenerCtrl = ['$scope', '$http', 'ModelService', function ($scope, $http, service) {
    $scope.endListeners = service.getEndListeners();

    $scope.processGatewayEndListenerChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };
}]

var DefStartNoneStartListenerCtrl = ['$scope', '$http', 'ModelService', function ($scope, $http, service) {
    $scope.startListeners = service.getStartListeners();
    $scope.processStartNoneStartListenerChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };
}]

var DefEndNoneEndListenerCtrl = ['$scope', '$http', 'ModelService', function ($scope, $http, service) {
    $scope.endListeners = service.getEndListeners();
    $scope.processEndNoneEndListenerChanged = function () {
        $scope.updatePropertyInModel($scope.property);
    };
}]

modeler.controller("activitiModeler", DefRemarkCtrl);
modeler.controller("activitiModeler", DefProcessIdCtrl);
modeler.controller("activitiModeler", DefProcessNameCtrl);
modeler.controller("activitiModeler", DefGatewayStartListenerCtrl);
modeler.controller("activitiModeler", DefGatewayEndListenerCtrl);
modeler.controller("activitiModeler", DefStartNoneStartListenerCtrl);
modeler.controller("activitiModeler", DefEndNoneEndListenerCtrl);