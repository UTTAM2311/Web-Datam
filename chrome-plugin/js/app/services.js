"use strict";

/**
 * Reference:
 * http://kirkbushell.me/angular-js-using-ng-resource-in-a-more-restful-manner/
 */

var restModule = angular.module('mainApp.rest', []);

restModule.factory('RestResource', ['$resource', function ($resource) {
    return function (url, params, methods) {

        var defaults = {
            update: { 
                method: 'put',
                isArray: false
            },
            create: { 
                method: 'post',
                isArray: false
            }
        };

        // id validation
        function isIdTemp(idStr) {
            console.log('id string : ' + idStr);

            if(!idStr)
                return true;

            if(idStr.indexOf('temp-') > -1)
                return true;

            return false;
        }

        // Add update and create as methods
        methods = angular.extend(defaults, methods);

        // Update the default 'save' method behavior
        var resource = $resource(url, params, methods);
        resource.prototype.$save = function () {

            console.log("save method triggered");
            console.log(this);

            if (isIdTemp(this._id)) {
                return this.$create();
            } else {
                return this.$update();
            }
        };

        return resource;
    };
}]);

restModule.factory('Note', ['RestResource', function(RestResource) {

    var url = 'http://localhost:3000/api/v1/note/:id';
    var params = {
        id: '@id'
    };
    var methods = {
        query: {
            params: {
                multiple: 's'
            },
            isArray: true
        },
        saveNote: {
            method: 'put',
            params: {
                action: 'add_note'
            }
        }
    };

    var NoteResource = RestResource(url, params, methods);

    return NoteResource;
}]);

restModule.factory('Tag', ['RestResource', function(RestResource) {

    var url = 'http://localhost:3000/api/v1/tag/:id';
    var params = {
        id: '@id'
    };
    var methods = {
        query: {
            method: 'get',
            params: {}
        },
    };

    var TagResource = RestResource(url, params, methods);

    return TagResource;
}]);

restModule.factory('ToDo', ['RestResource', function(RestResource) {
    
    var url = 'http://localhost:3000/api/v1/todo/:from_timestamp';
    var params = {
        from_timestamp : '@from_timestamp'
    };

    var methods = {
        query: {
            method: 'get',
            params: {}
        }
    };

    var TodoResource = RestResource(url, params, methods);

    return TodoResource;
}]);

restModule.factory('Auth', ['RestResource', function(RestResource) {
    
    var url = 'http://localhost:3000/auth/:resource';
    var params = {
        resource : '@resource'
    };
    
    var methods = {
        signIn: {
            method: 'post',
            params: {
                resource: 'signin'
            }
        },
        signOut: {
            method: 'post',
            params: {
                resource: 'signout'
            }
        },
        signUp: {
            method: 'post',
            params: {
                resource: 'signup'
            }
        }
    };
    
    var AuthResource = RestResource(url, params, methods);
    
    return AuthResource;
}]);



