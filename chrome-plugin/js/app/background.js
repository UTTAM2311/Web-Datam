'use strict';

app.run(function($rootScope, $http, $localStorage, ToDo) {

    // TODO make this callable from anywhere ..
    function updateTodos() {

//         console.log("Header Access Token : " + $localStorage.header_accessToken);
//         console.log("Header Access ID : " + $localStorage.header_accessId);

        // verify if auth tokens are present 
        if(!$localStorage.header_accessToken || $localStorage.header_accessToken == null)
            return;

        // set the ngResourse headers
        $http.defaults.headers.common['x-access-token'] = $localStorage.header_accessToken;
        $http.defaults.headers.common['x-access-id'] = $localStorage.header_accessId;

        var criteria = {};
//         criteria.from_timestamp = $localStorage.last_update_timestamp;

        // fetch TODOs 
        ToDo.query(criteria, function(response) {

            // update local storage
            // TODO clean it up - do merge instead of replacing
            $localStorage.notes = response.notes || [];
            $localStorage.tags = response.tags || [];
            $localStorage.user = response.network || {};

            // reset the last_update_timestamp
            var timeStamp = new Date();
            $localStorage.last_update_timestamp = timeStamp.getTime();
        }, function(err) {
            
            console.log("Error fetchin TODOs :");
            console.log(err);

            // handle unAuthorized error
            if(err.status === 401) {
                resetAuth();
            }
        });

    };


    // update TODOs in regular intervals
    var todoIntervalId = setInterval(updateTodos, 30000);


    /**
     * Reset access header values.
     */
    function resetAuth() {
        // reset header values
        $localStorage.header_accessToken = null;
        $localStorage.header_accessId = null;
    }

});