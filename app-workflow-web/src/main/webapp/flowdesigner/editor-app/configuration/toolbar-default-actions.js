/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
'use strict';

var KISBPM = KISBPM || {};
KISBPM.TOOLBAR = {
    ACTIONS: {

        saveModel: function (services) {

            var modal = services.$modal({
                backdrop: true,
                keyboard: true,
                template: ACTIVITI.CONFIG.contextRoot + '/flowdesigner/editor-app/popups/save-model.html?version=' + Date.now(),
                scope: services.$scope
            });

        },

        undo: function (services) {

            // Get the last commands
            var lastCommands = services.$scope.undoStack.pop();

            if (lastCommands) {
                // Add the commands to the redo stack
                services.$scope.redoStack.push(lastCommands);

                // Force refresh of selection, might be that the undo command
                // impacts properties in the selected item
                if (services.$rootScope && services.$rootScope.forceSelectionRefresh) {
                    services.$rootScope.forceSelectionRefresh = true;
                }

                // Rollback every command
                for (var i = lastCommands.length - 1; i >= 0; --i) {
                    lastCommands[i].rollback();
                }

                // Update and refresh the canvas
                services.$scope.editor.handleEvents({
                    type: ORYX.CONFIG.EVENT_UNDO_ROLLBACK,
                    commands: lastCommands
                });

                // Update
                services.$scope.editor.getCanvas().update();
                services.$scope.editor.updateSelection();
            }

            var toggleUndo = false;
            if (services.$scope.undoStack.length == 0) {
                toggleUndo = true;
            }

            var toggleRedo = false;
            if (services.$scope.redoStack.length > 0) {
                toggleRedo = true;
            }

            if (toggleUndo || toggleRedo) {
                for (var i = 0; i < services.$scope.items.length; i++) {
                    var item = services.$scope.items[i];
                    if (toggleUndo && item.action === 'KISBPM.TOOLBAR.ACTIONS.undo') {
                        services.$scope.safeApply(function () {
                            item.enabled = false;
                        });
                    }
                    else if (toggleRedo && item.action === 'KISBPM.TOOLBAR.ACTIONS.redo') {
                        services.$scope.safeApply(function () {
                            item.enabled = true;
                        });
                    }
                }
            }
        },

        redo: function (services) {

            // Get the last commands from the redo stack
            var lastCommands = services.$scope.redoStack.pop();

            if (lastCommands) {
                // Add this commands to the undo stack
                services.$scope.undoStack.push(lastCommands);

                // Force refresh of selection, might be that the redo command
                // impacts properties in the selected item
                if (services.$rootScope && services.$rootScope.forceSelectionRefresh) {
                    services.$rootScope.forceSelectionRefresh = true;
                }

                // Execute those commands
                lastCommands.each(function (command) {
                    command.execute();
                });

                // Update and refresh the canvas
                services.$scope.editor.handleEvents({
                    type: ORYX.CONFIG.EVENT_UNDO_EXECUTE,
                    commands: lastCommands
                });

                // Update
                services.$scope.editor.getCanvas().update();
                services.$scope.editor.updateSelection();
            }

            var toggleUndo = false;
            if (services.$scope.undoStack.length > 0) {
                toggleUndo = true;
            }

            var toggleRedo = false;
            if (services.$scope.redoStack.length == 0) {
                toggleRedo = true;
            }

            if (toggleUndo || toggleRedo) {
                for (var i = 0; i < services.$scope.items.length; i++) {
                    var item = services.$scope.items[i];
                    if (toggleUndo && item.action === 'KISBPM.TOOLBAR.ACTIONS.undo') {
                        services.$scope.safeApply(function () {
                            item.enabled = true;
                        });
                    }
                    else if (toggleRedo && item.action === 'KISBPM.TOOLBAR.ACTIONS.redo') {
                        services.$scope.safeApply(function () {
                            item.enabled = false;
                        });
                    }
                }
            }
        },

        cut: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxEditPlugin(services.$scope).editCut();
            for (var i = 0; i < services.$scope.items.length; i++) {
                var item = services.$scope.items[i];
                if (item.action === 'KISBPM.TOOLBAR.ACTIONS.paste') {
                    services.$scope.safeApply(function () {
                        item.enabled = true;
                    });
                }
            }
        },

        copy: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxEditPlugin(services.$scope).editCopy();
            for (var i = 0; i < services.$scope.items.length; i++) {
                var item = services.$scope.items[i];
                if (item.action === 'KISBPM.TOOLBAR.ACTIONS.paste') {
                    services.$scope.safeApply(function () {
                        item.enabled = true;
                    });
                }
            }
        },

        paste: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxEditPlugin(services.$scope).editPaste();
        },

        deleteItem: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxEditPlugin(services.$scope).editDelete();
        },
        addBendPoint: function (services) {

            var dockerPlugin = KISBPM.TOOLBAR.ACTIONS._getOryxDockerPlugin(services.$scope);

            var enableAdd = !dockerPlugin.enabledAdd();
            dockerPlugin.setEnableAdd(enableAdd);
            if (enableAdd) {
                dockerPlugin.setEnableRemove(false);
                document.body.style.cursor = 'pointer';
            }
            else {
                document.body.style.cursor = 'default';
            }
        },

        removeBendPoint: function (services) {

            var dockerPlugin = KISBPM.TOOLBAR.ACTIONS._getOryxDockerPlugin(services.$scope);

            var enableRemove = !dockerPlugin.enabledRemove();
            dockerPlugin.setEnableRemove(enableRemove);
            if (enableRemove) {
                dockerPlugin.setEnableAdd(false);
                document.body.style.cursor = 'pointer';
            }
            else {
                document.body.style.cursor = 'default';
            }
        },

        /**
         * Helper method: fetches the Oryx Edit plugin from the provided scope,
         * if not on the scope, it is created and put on the scope for further use.
         *
         * It's important to reuse the same EditPlugin while the same scope is active,
         * as the clipboard is stored for the whole lifetime of the scope.
         */
        _getOryxEditPlugin: function ($scope) {
            if ($scope.oryxEditPlugin === undefined || $scope.oryxEditPlugin === null) {
                $scope.oryxEditPlugin = new ORYX.Plugins.Edit($scope.editor);
            }
            return $scope.oryxEditPlugin;
        },

        zoomIn: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxViewPlugin(services.$scope).zoom([1.0 + ORYX.CONFIG.ZOOM_OFFSET]);
        },

        zoomOut: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxViewPlugin(services.$scope).zoom([1.0 - ORYX.CONFIG.ZOOM_OFFSET]);
        },

        zoomActual: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxViewPlugin(services.$scope).setAFixZoomLevel(1);
        },

        zoomFit: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxViewPlugin(services.$scope).zoomFitToModel();
        },

        alignVertical: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxArrangmentPlugin(services.$scope).alignShapes([ORYX.CONFIG.EDITOR_ALIGN_MIDDLE]);
        },

        alignHorizontal: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxArrangmentPlugin(services.$scope).alignShapes([ORYX.CONFIG.EDITOR_ALIGN_CENTER]);
        },

        sameSize: function (services) {
            KISBPM.TOOLBAR.ACTIONS._getOryxArrangmentPlugin(services.$scope).alignShapes([ORYX.CONFIG.EDITOR_ALIGN_SIZE]);
        },

        closeEditor: function () {
            closeModalCenter("");
        },

        /**
         * Helper method: fetches the Oryx View plugin from the provided scope,
         * if not on the scope, it is created and put on the scope for further use.
         */
        _getOryxViewPlugin: function ($scope) {
            if ($scope.oryxViewPlugin === undefined || $scope.oryxViewPlugin === null) {
                $scope.oryxViewPlugin = new ORYX.Plugins.View($scope.editor);
            }
            return $scope.oryxViewPlugin;
        },

        _getOryxArrangmentPlugin: function ($scope) {
            if ($scope.oryxArrangmentPlugin === undefined || $scope.oryxArrangmentPlugin === null) {
                $scope.oryxArrangmentPlugin = new ORYX.Plugins.Arrangement($scope.editor);
            }
            return $scope.oryxArrangmentPlugin;
        },

        _getOryxDockerPlugin: function ($scope) {
            if ($scope.oryxDockerPlugin === undefined || $scope.oryxDockerPlugin === null) {
                $scope.oryxDockerPlugin = new ORYX.Plugins.AddDocker($scope.editor);
            }
            return $scope.oryxDockerPlugin;
        }
    }
};

/** Custom controller for the save dialog */
var SaveModelCtrl = ['$rootScope', '$scope', '$http', '$route', '$location', 'ModelService',
    function ($rootScope, $scope, $http, $route, $location, ModelService) {

        var modelMetaData = $scope.editor.getModelMetaData();

        var description = '';
        if (modelMetaData.description) {
            description = modelMetaData.description;
        }

        var saveDialog = {
            'name': $scope.editor.getJSON().properties.name,
            'processId': $scope.editor.getJSON().properties.process_id,
            'description': description
        };

        $scope.saveDialog = saveDialog;

        var json = $scope.editor.getJSON();
        json = JSON.stringify(json);

        $scope.status = {
            loading: false
        };

        $scope.close = function () {
            $scope.$hide();
        };

        $scope.saveOrDeploy = function (isDeploy) {
            var operateType = EDITOR.UTIL.getParameterByName('operateType');
            if (operateType == "2") { //修改
                $scope.save(isDeploy);
            } else { // 新增
                var processId = $scope.editor.getJSON().properties.process_id;
                if (!new RegExp("^[a-zA-Z][a-zA-Z0-9_]{2,19}$").test(processId)) {
                    $scope.error = "流程编号请输入3-20个以字母开头，字母、数字及_！";
                    $scope.status.loading = false;
                    return;
                }
                $http({
                    method: "GET",
                    params: {
                        definitionKey: processId
                    },
                    url: ACTIVITI.CONFIG.contextRoot + "/workflow/procdef/verify/defkey"
                }).success(function (data) {
                    if (data == "0") {
                        $scope.save(isDeploy);
                    } else {
                        $scope.error = "流程定义编号为：" + processId + "，已存在！";
                    }
                });
            }
        };

        $scope.save = function (isDeploy) {
            $scope.error = " ";
            $scope.status = {
                loading: true
            };
            var processName = $scope.saveDialog.name;
            modelMetaData.name = processName;
            modelMetaData.description = $scope.saveDialog.description;
            var json = $scope.editor.getJSON();
            if (!$scope.verifyModel(json)) {
                return;
            }
            json = JSON.stringify(json);

            var selection = $scope.editor.getSelection();
            $scope.editor.setSelection([]);

            var svgClone = $scope.editor.getCanvas().getSVGRepresentation(true);
            $scope.editor.setSelection(selection);
            if ($scope.editor.getCanvas().properties["oryx-showstripableelements"] === false) {
                var stripOutArray = jQuery(svgClone).find(".stripable-element");
                for (var i = stripOutArray.length - 1; i >= 0; i--) {
                    stripOutArray[i].remove();
                }
            }

            var stripOutArray = jQuery(svgClone).find(".stripable-element-force");
            for (var i = stripOutArray.length - 1; i >= 0; i--) {
                stripOutArray[i].remove();
            }

            var svgDOM = DataManager.serialize(svgClone);

            var config = {
                status: isDeploy,
                modelName: processName,
                json_xml: json,
                svg_xml: svgDOM,
                description: $scope.saveDialog.description
            };

            $http({
                method: 'POST',
                data: config,
                ignoreErrors: true,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj) {
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    }
                    return str.join("&");
                },
                url: KISBPM.URL.putModel(modelMetaData.modelId)
            })
                .success(function (data, status, headers, config) {
                    $scope.editor.handleEvents({
                        type: ORYX.CONFIG.EVENT_SAVED
                    });
                    $scope.modelData.name = $scope.saveDialog.name;
                    var saveEvent = {
                        type: KISBPM.eventBus.EVENT_TYPE_MODEL_SAVED,
                        model: config,
                        modelId: modelMetaData.modelId,
                        eventType: 'update-model'
                    };
                    KISBPM.eventBus.dispatch(KISBPM.eventBus.EVENT_TYPE_MODEL_SAVED, saveEvent);
                    if (data == "1") {
                        $scope.success = "提示:\"流程定义更新成功！\"";
                    } else {
                        $scope.error = "提示:" + data;
                    }
                    $scope.status.loading = false;
                })
                .error(function (data, status, headers, config) {
                    $scope.error = "流程定义不合法，更新失败！";
                    $scope.status.loading = false;
                });
        };

        $scope.verifyModel = function (formJson) {
            var childShapes = formJson.childShapes;
            if (childShapes.length == 0) {
                $scope.error = "流程定义不合法，请重新编辑！";
                $scope.status.loading = false;
                return false;
            }

            if (!$scope.validateStartNoneEvent(childShapes)) {
                return false;
            }

            if (!$scope.validateUserTask(childShapes)) {
                return false;
            }

            if (!$scope.validateSequenceFlow(childShapes)) {
                return false;
            }

            if (!$scope.validateEndNoneEvent(childShapes)) {
                return false;
            }

            var processName = formJson.properties.name;
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5（）]{2,20}$").test(processName)) {
                $scope.error = "流程名称只可输入2-20位英文、数字、汉字、括号和_！";
                $scope.status.loading = false;
                return false;
            }

            var elmentIds = new Array();
            for (var i = 0; i < childShapes.length; i++) {
                var props = childShapes[i].properties;
                for (var j = 0; j < elmentIds.length; j++) {
                    if (elmentIds[j] == props.overrideid && props.overrideid != "") {
                        $scope.error = "节点编号【" + props.overrideid + "】重复，请先修改！";
                        $scope.status.loading = false;
                        return false;
                    }
                }
                if (props.overrideid != "") {
                    elmentIds.push(props.overrideid);
                }
                var stencil = childShapes[i].stencil.id;
                if (stencil == "UserTask") {
                    if (!new RegExp("^[a-zA-Z][a-zA-Z0-9_]{1,19}$").test(props.overrideid)) {
                        $scope.error = "节点编号【" + props.overrideid + "】定义不合法，只可输入2-20个以字母开头，字母、数字及_.";
                        $scope.status.loading = false;
                        return false;
                    }
                    if (!$scope.validateParticipant(props)) {
                        return false;
                    }
                } else {
                    if (props.overrideid) {
                        if (!new RegExp("^[a-zA-Z][a-zA-Z0-9_]{1,19}$").test(props.overrideid)) {
                            $scope.error = "节点编号【" + props.overrideid + "】定义不合法，只可输入2-20个以字母开头，字母、数字及_.";
                            $scope.status.loading = false;
                            return false;
                        }
                    }
                }
                var nodeShape = childShapes[i];
                if (stencil != "SequenceFlow") {
                    if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5（）]{2,20}$").test(props.name)) {
                        $scope.error = "节点编号【" + props.overrideid + "】的节点名称定义不合法，只可输入2-20位英文、数字、汉字、括号和_";
                        $scope.status.loading = false;
                        return false;
                    }
                    if (!$scope.validateRoute(nodeShape)) {
                        return false;
                    }
                } else {
                    if (props.name) {
                        if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5（）]{2,20}$").test(props.name)) {
                            $scope.error = "节点编号【" + props.overrideid + "】的节点名称定义不合法，只可输入2-20位英文数字汉字和_";
                            $scope.status.loading = false;
                            return false;
                        }
                    }
                }
                if (stencil == "ExclusiveGateway") {
                    if (!$scope.validateExclusiveGateway(childShapes, nodeShape)) {
                        return false;
                    }
                }
            }

            return true;
        };

        /**
         * 校验决策节点
         * @param nodeShape
         */
        $scope.validateExclusiveGateway = function (childShapes, nodeShape) {
            var outgoing = nodeShape.outgoing;
            if (outgoing.length == 1) {
                return true;
            }
            var props = nodeShape.properties;
            for (var i = 0; i < outgoing.length; i++) {
                var resId = outgoing[i].resourceId;
                for (var j = 0; j < childShapes.length; j++) {
                    var stencilId = childShapes[j].stencil.id;
                    var targetResId = childShapes[j].resourceId;
                    if (stencilId != "SequenceFlow" || targetResId != resId) {
                        continue;
                    }
                    var targetProps = childShapes[j].properties;
                    if (targetProps.conditionsequenceflow == "") {
                        $scope.error = "决策分支【" + props.name + "】，流转线的流转条件不能为空！";
                        $scope.status.loading = false;
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * 校验节点路由
         *
         * @param taskProps
         */
        $scope.validateRoute = function (nodeShape) {
            var stencilId = nodeShape.stencil.id;
            if (stencilId == "EndNoneEvent") {
                return true;
            }
            var props = nodeShape.properties;
            var outgoing = nodeShape.outgoing;
            if (outgoing.length == 0) {
                $scope.error = "节点【" + props.name + "】没有流转线！";
                $scope.status.loading = false;
                return false;
            }
            return true;
        };

        /**
         * 校验流程审批人、角色、机构
         * @param taskProps
         */
        $scope.validateParticipant = function (taskProps) {
            if (taskProps.approvetype == "1") {//提交人
                return true;
            } else if (taskProps.approvetype == "5") {//指定用户
                if (taskProps.approveusername == "") {
                    $scope.error = "节点【" + taskProps.name + "】的审批用户不能为空！";
                    $scope.status.loading = false;
                    return false;
                }
            } else if (taskProps.approvetype == "4") {//某机构某角色
                if (taskProps.approveorgancode == "" || taskProps.approverolecode == "") {
                    $scope.error = "节点【" + taskProps.name + "】的审批角色和机构不能为空！";
                    $scope.status.loading = false;
                    return false;
                }
            } else {
                if (taskProps.approverolecode == "") {
                    $scope.error = "节点【" + taskProps.name + "】的审批角色不能为空！";
                    $scope.status.loading = false;
                    return false;
                }
            }
            return true;
        };

        $scope.validateStartNoneEvent = function (childShapes) {
            var count = 0;
            for (var i = 0; i < childShapes.length; i++) {
                var stencil = childShapes[i].stencil.id;
                if (stencil == "StartNoneEvent") {
                    count++;
                }
            }

            if (count > 1) {
                $scope.error = "开始节点只能存在一个！";
                $scope.status.loading = false;
                return false;
            }

            if (count == 0) {
                $scope.error = "请选择开始节点！";
                $scope.status.loading = false;
                return false;
            }

            return true;
        };

        $scope.validateSequenceFlow = function (childShapes) {
            var count = 0;
            for (var i = 0; i < childShapes.length; i++) {
                var stencil = childShapes[i].stencil.id;
                if (stencil == "SequenceFlow") {
                    count++;
                }
            }
            if (count == 0) {
                $scope.error = "请选择分支流转线！";
                $scope.status.loading = false;
                return false;
            }

            for (var i = 0; i < childShapes.length; i++) {
                var outgoing = childShapes[i].outgoing;
                var stencil = childShapes[i].stencil.id;
                if (stencil == "SequenceFlow" && outgoing.length == 0) {
                    $scope.error = "有流转线没有指向具体节点！";
                    $scope.status.loading = false;
                    return false;
                }
            }

            return true;
        };

        $scope.validateUserTask = function (childShapes) {
            var count = 0;
            for (var i = 0; i < childShapes.length; i++) {
                var stencil = childShapes[i].stencil.id;
                if (stencil == "UserTask") {
                    count++;
                }
            }
            if (count == 0) {
                $scope.error = "请选择任务节点！";
                $scope.status.loading = false;
                return false;
            }
            return true;
        };

        $scope.validateEndNoneEvent = function (childShapes) {
            var count = 0;
            for (var i = 0; i < childShapes.length; i++) {
                var stencil = childShapes[i].stencil.id;
                if (stencil == "EndNoneEvent") {
                    count++;
                }
            }
            if (count == 0) {
                $scope.error = "请选择结束节点！";
                $scope.status.loading = false;
                return false;
            }
            return true;
        };
    }];