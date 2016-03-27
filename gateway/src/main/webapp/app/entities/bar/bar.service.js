(function() {
    'use strict';
    angular
        .module('gatewayApp')
        .factory('Bar', Bar);

    Bar.$inject = ['$resource'];

    function Bar ($resource) {
        var resourceUrl =  'bar/' + 'api/bars/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
