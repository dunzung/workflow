/**
 *
 * @author zhijund
 * @since 2015-09-24
 */
/**
 * 映射审批类型
 *
 * @author zhijund
 * @since 2015-09-24
 */
'use strict';
var modeler = angular.module('activitiModeler');
modeler.service("ReflectTypeService", ["$rootScope", function ($rootScope) {

    var service = {
        typeKey: "oryx-approvetype",
        organNameKey: "oryx-approveorganname",
        roleNameKey: "oryx-approverolename",
        userNameKey: "oryx-approveusername",
        /**
         * 角色 & 提交人所在机构角色 &某机构某角色& 提交上级机构某角色 & 提交所在机构角色或上级机构某角色
         * @param properties
         * @returns {boolean}
         */
        roleSwitch: function (properties) {
            var reflectType = service.getPropertyValue(service.typeKey, properties);
            return (reflectType != 1 && reflectType != 5);
        },
        /**
         *  某机构某角色
         * @param properties
         * @returns {boolean}
         */
        organSwitch: function (properties) {
            var reflectType = service.getPropertyValue(service.typeKey, properties);
            return (reflectType == 4)
        },
        /**
         * 指定用户
         * @param properties
         * @returns {boolean}
         */
        userSwitch: function (properties) {
            var reflectType = service.getPropertyValue(service.typeKey, properties);
            return (reflectType == 5)
        },
        notifyChangeTypeEvent: function () {
            $rootScope.$broadcast("handleChangeReflectTypeBroadcast");
        },
        notifyRoleCodeEvent: function () {
            $rootScope.$broadcast("handleRoleCodeBroadcast");
        },
        notifyOrganCodeEvent: function () {
            $rootScope.$broadcast("handleOrganCodeBroadcast");
        },
        notifyUseridEvent: function () {
            $rootScope.$broadcast("handleUseridBroadcast");
        },
        getPropertyValue: function (propName, properties) {
            for (var i = 0; i < properties.length; i++) {
                var item = properties[i];
                if (item.key == propName) {
                    return item.value;
                }
            }
            return "";
        }
    }

    return service;
}]);

var DefReflectTypeCtrl = ['$scope', '$http', 'ReflectTypeService', function ($scope, $http, service) {

    $scope.reflectTypeModel = {}

    $scope.processReflectTypeChanged = function () {
        $scope.updatePropertyInModel($scope.property);
        service.notifyChangeTypeEvent()
    };
}]

/**
 * 角色
 * @type {*[]}
 */
var DefPopupRoleCtrl = ['$scope', '$http', 'ReflectTypeService', "$q", function ($scope, $http, service, $q) {

    var hasSwitch = service.roleSwitch($scope.selectedItem.properties)
    $scope.roleModel = {
        disableInput: hasSwitch
    }

    $scope.popupRole = function () {
        var deferred = $q.defer();
        showModalCenter(ACTIVITI.CONFIG.contextRoot + "/workflow/participant/popup/role/index", function (roles) {
            var roleNames = "";
            var roleCodes = "";
            for (var i in roles) {
                roleNames += (i == 0) ? roles[i].roleName : "," + roles[i].roleName;
                roleCodes += (i == 0) ? roles[i].roleCode : "," + roles[i].roleCode;
            }
            $scope.property.value = roleNames;
            $scope.property.roleCodes = roleCodes;
            deferred.resolve();

        }, "50%", "50%", "角色配置")

        var promise = deferred.promise;
        promise.then(function (data) {
            $scope.updatePropertyInModel($scope.property);
            service.notifyRoleCodeEvent();
        })
    }
    $scope.$on("handleChangeReflectTypeBroadcast", function () {
        $scope.roleModel.disableInput = service.roleSwitch($scope.selectedItem.properties)
        if ($scope.roleModel.disableInput == false) {
            $scope.property.value = "";
            $scope.updatePropertyInModel($scope.property);
            service.notifyRoleCodeEvent();
        }
    })
}]


var DefRoleCodeCtrl = ['$scope', '$http', 'ReflectTypeService', function ($scope, $http, service) {
    $scope.$on("handleRoleCodeBroadcast", function () {
        var properties = $scope.selectedItem.properties
        if (service.roleSwitch(properties)) {
            for (var i = 0; i < properties.length; i++) {
                var item = properties[i];
                if (item.key == service.roleNameKey && item.roleCodes) {
                    $scope.property.value = item.roleCodes;
                    $scope.updatePropertyInModel($scope.property);
                    break;
                }
            }
        } else {
            $scope.property.value = "";
            $scope.updatePropertyInModel($scope.property);
        }
    })
}]

/**
 * 机构
 * @type {*[]}
 */
var DefPopupOrganCtrl = ['$scope', '$http', 'ReflectTypeService', "$q", function ($scope, $http, service, $q) {

    var hasSwitch = service.organSwitch($scope.selectedItem.properties)
    $scope.organModel = {
        disableInput: hasSwitch
    }

    $scope.popupOrgan = function () {
        var deferred = $q.defer();
        showModalCenter(ACTIVITI.CONFIG.contextRoot + "/workflow/participant/popup/organ/index", function (organs) {
            var orgNames = "";
            var orgCodes = "";
            for (var i in organs) {
                orgNames += (i == 0) ? organs[i].orgName : "," + organs[i].orgName;
                orgCodes += (i == 0) ? organs[i].orgCode : "," + organs[i].orgCode;
            }
            $scope.property.value = orgNames;
            $scope.property.organCodes = orgCodes;
            deferred.resolve();
        }, "20%", "80%", "机构配置")

        var promise = deferred.promise;
        promise.then(function (data) {
            $scope.updatePropertyInModel($scope.property);
            service.notifyOrganCodeEvent();
        })
    }

    $scope.$on("handleChangeReflectTypeBroadcast", function () {
        $scope.organModel.disableInput = service.organSwitch($scope.selectedItem.properties)
        if ($scope.organModel.disableInput == false) {
            $scope.property.value = "";
            $scope.updatePropertyInModel($scope.property);
            service.notifyOrganCodeEvent();
        }
    })
}]

var DefOrganCodeCtrl = ['$scope', '$http', 'ReflectTypeService', function ($scope, $http, service) {
    $scope.$on("handleOrganCodeBroadcast", function () {
        var properties = $scope.selectedItem.properties
        if (service.organSwitch(properties)) {
            for (var i = 0; i < properties.length; i++) {
                var item = properties[i];
                if (item.key == service.organNameKey && item.organCodes) {
                    $scope.property.value = item.organCodes;
                    $scope.updatePropertyInModel($scope.property);
                    break;
                }
            }
        } else {
            $scope.property.value = "";
            $scope.updatePropertyInModel($scope.property);
        }
    })
}]


/**
 * 用户
 * @type {*[]}
 */
var DefPopupUserCtrl = ['$scope', '$http', 'ReflectTypeService', "$q", function ($scope, $http, service, $q) {

    var hasSwitch = service.userSwitch($scope.selectedItem.properties)
    $scope.userModel = {
        disableInput: hasSwitch
    }

    $scope.popupUser = function () {
        var deferred = $q.defer();
        showModalCenter(ACTIVITI.CONFIG.contextRoot + "/workflow/participant/popup/user/index", function (users) {
            var realNames = "";
            var userIds = "";
            for (var i in users) {
                realNames += (i == 0) ? users[i].name : "," + users[i].name;
                userIds += (i == 0) ? users[i].id : "," + users[i].id;
            }
            $scope.property.value = realNames;
            $scope.property.userIds = userIds;
            deferred.resolve();
        }, "50%", "50%", "用户配置")

        var promise = deferred.promise;
        promise.then(function (data) {
            $scope.updatePropertyInModel($scope.property);
            service.notifyUseridEvent();
        })
    }

    $scope.$on("handleChangeReflectTypeBroadcast", function () {
        $scope.userModel.disableInput = service.userSwitch($scope.selectedItem.properties)
        if ($scope.userModel.disableInput == false) {
            $scope.property.value = "";
            $scope.updatePropertyInModel($scope.property);
            service.notifyUseridEvent();
        }
    })
}]

var DefUseridCtrl = ['$scope', '$http', 'ReflectTypeService', function ($scope, $http, service) {

    $scope.$on("handleUseridBroadcast", function () {
        var properties = $scope.selectedItem.properties
        if (service.userSwitch(properties)) {
            for (var i = 0; i < properties.length; i++) {
                var item = properties[i];
                if (item.key == service.userNameKey && item.userIds) {
                    $scope.property.value = item.userIds;
                    $scope.updatePropertyInModel($scope.property);
                    break;
                }
            }
        } else {
            $scope.property.value = "";
            $scope.updatePropertyInModel($scope.property);
        }
    })
}]


modeler.controller("activitiModeler", DefPopupUserCtrl);
modeler.controller("activitiModeler", DefPopupOrganCtrl);
modeler.controller("activitiModeler", DefPopupRoleCtrl);
modeler.controller("activitiModeler", DefReflectTypeCtrl);
modeler.controller("activitiModeler", DefUseridCtrl);
modeler.controller("activitiModeler", DefOrganCodeCtrl);
modeler.controller("activitiModeler", DefRoleCodeCtrl)