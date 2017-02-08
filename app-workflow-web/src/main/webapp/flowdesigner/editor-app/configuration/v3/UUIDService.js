/**
 * UUIDService
 *
 * @author zhijund
 * @since 2015-09-24
 */
'use strict';
var modeler = angular.module('activitiModeler');
modeler.service("UUIDService", ["$rootScope", function ($rootScope) {

    var service = {

        getSuffixNumber: function (propId, stencilId) {
            return propId.substr(stencilId.length);
        },

        getMaxNumber: function (propIds) {
            if (propIds.length == 0) {
                return 0;
            }
            return Math.max.apply(null, propIds);
        },

        genUUID: function (stencilId, childShapes) {
            var propIds = new Array();
            for (var i = 0; i < childShapes.length; i++) {
                var props = childShapes[i].properties;
                if (stencilId == childShapes[i].stencil.id) {
                    var suffix_number = service.getSuffixNumber(props.overrideid, stencilId);
                    propIds.push(suffix_number)
                }
            }
            var maxNumber = service.getMaxNumber(propIds);
            return stencilId + (maxNumber + 1);
        }

    }

    return service;
}]);
