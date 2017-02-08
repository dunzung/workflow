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
KISBPM.HTML_URL = "../../flowdesigner/"
KISBPM.PROPERTY_CONFIG =
{
    "custom-text-def-sequenceflowcondition": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-sequenceflowcondition.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-sequenceflowcondition.html"
    },
    "custom-combox-def-votetype": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-votetype.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-votetype.html"
    },
    "custom-combox-def-gatewaystartlistener": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-gatewaystartlistener.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-gatewaystartlistener.html"
    },
    "custom-combox-def-gatewayendlistener": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-gatewayendlistener.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-gatewayendlistener.html"
    },
    "custom-combox-def-startnonestartlistener": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-startnonestartlistener.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-startnonestartlistener.html"
    },
    "custom-combox-def-endnonestartlistener": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-endnonestartlistener.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-endnonestartlistener.html"
    },
    "custom-combox-def-taskstartlistener": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-taskstartlistener.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-taskstartlistener.html"
    },
    "custom-combox-def-taskendlistener": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-taskendlistener.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-taskendlistener.html"
    },

    "kisbpm-multiinstance": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-signtype.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-signtype.html"
    },
    "custom-text-def-signassignees": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-signassignees.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-signassignees.html"
    },
    "custom-text-def-assignee": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-assignee.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-assignee.html"
    },
    "custom-text-def-signvariable": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-signvariable.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-signvariable.html"
    },
    "custom-text-def-signcompletedcondition": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-signcompletedcondition.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-signcompletedcondition.html"
    },
    "custom-popup-def-organname": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-popup-def-organname.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-popup-def-organname.html"
    },
    "custom-popup-def-rolename": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-popup-def-rolename.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-popup-def-rolename.html"
    },
    "custom-popup-def-username": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-popup-def-username.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-popup-def-username.html"
    },
    "custom-hidden-def-organcode": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-hidden-def-organcode.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-hidden-def-organcode.html"
    },
    "custom-hidden-def-rolecode": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-hidden-def-rolecode.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-hidden-def-rolecode.html"
    },
    "custom-hidden-def-userid": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-hidden-def-userid.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-hidden-def-userid.html"
    },
    "custom-text-def-processid": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-processid.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-processid.html"
    },
    "custom-text-def-processname": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-processname.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-processname.html"
    },
    "custom-text-def-attrid": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-attrid.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-attrid.html"
    },
    "custom-text-def-attrname": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-attrname.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-attrname.html"
    },
    "custom-combox-def-permitdelegate": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-permitdelegate.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-permitdelegate.html"
    },
    "custom-combox-def-reflecttype": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-reflecttype.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-reflecttype.html"
    },
    "custom-combox-def-backrule": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-backrule.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-combox-def-backrule.html"
    },
    "string": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-attrname.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-attrname.html"
    },
    "boolean": {
        "templateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/boolean-property-template.html"
    },
    "text": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-remark.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/v3/page/custom-text-def-remark.html"
    },
    //"kisbpm-multiinstance": {
    //    "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/multiinstance-property-write-template.html",
    //    "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/multiinstance-property-write-template.html"
    //},
    "oryx-formproperties-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/form-properties-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/form-properties-write-template.html"
    },
    "oryx-executionlisteners-multiplecomplex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/execution-listeners-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/execution-listeners-write-template.html"
    },
    "oryx-tasklisteners-multiplecomplex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/task-listeners-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/task-listeners-write-template.html"
    },
    "oryx-eventlisteners-multiplecomplex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/event-listeners-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/event-listeners-write-template.html"
    },
    "oryx-usertaskassignment-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/assignment-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/assignment-write-template.html"
    },
    "oryx-servicetaskfields-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/fields-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/fields-write-template.html"
    },
    "oryx-callactivityinparameters-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/in-parameters-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/in-parameters-write-template.html"
    },
    "oryx-callactivityoutparameters-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/out-parameters-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/out-parameters-write-template.html"
    },
    "oryx-subprocessreference-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/subprocess-reference-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/subprocess-reference-write-template.html"
    },
    "oryx-formreference-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/form-reference-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/form-reference-write-template.html"
    },
    "oryx-sequencefloworder-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/sequenceflow-order-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/sequenceflow-order-write-template.html"
    },
    "oryx-conditionsequenceflow-complex": {
        "readModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/condition-expression-display-template.html",
        "writeModeTemplateUrl": KISBPM.HTML_URL + "editor-app/configuration/properties/condition-expression-write-template.html"
    }
};
