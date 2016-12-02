'use strict';

/**
 * Main controller
 */
app.controller("mainController", function($scope, $localStorage, $http, Note, Tag, Auth) {
    
    // $localStorage.header_accessToken = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI1NGQ2NWRlZjI5MDZlNzc2MGZlNWRiNTciLCJleHAiOjE0Mjk2NzU3NTgyNTYsImlhdCI6MTQyOTA3MDk1OH0.qSU9TOzwrZuMDGgCsHxYiDoywNJH5BN8v6OWNEM-Ruo';
    // $localStorage.header_accessId = '54d65def2906e7760fe5db57';
    

    // TODO remove $http dependency
    
    /**
     * Custom headers
     * - 'x-access-token'
     * - 'x-access-id'
     */
//     $http.defaults.headers.common['x-access-token']= $localStorage.header_accessToken;
//     $http.defaults.headers.common['x-access-id']= $localStorage.header_accessId;



    /* --- Local Storage --- */

    var $storage = $localStorage || {};

    if(!$storage.notes) {  $storage.notes = []; }
    if(!$storage.tags) {  $storage.tags = []; }
    if(!$storage.notesFilters) { $storage.notesFilters = { "all" : true }; }

    /* --- scope variables --- */

    // ref: https://docs.angularjs.org/api/ng/filter/date
    $scope.dateFormat = 'MMM d';
    $scope.todayDate = new Date();

    // user related
    $scope.user = $storage.user || {};
    $scope.userNetwork = $scope.user.userNetwork;

    // note related
    $scope.notes = $storage.notes;
    $scope.notesFilters = $storage.notesFilters;
    $scope.notesFilterStr = function(){

        // TODO fixit
        var tagNames = Object.keys($scope.notesFilters).filter(function(k){
            return $scope.notesFilters[k];
        });

        console.log(tagNames);
        return tagNames.join(', ');
    }

    $scope.currentNote = {};
    var emptyNote = {
        _id : '', 
        content : '',
        deadline : '',
        tag : null
    };

    // tag related
    $scope.tags = $storage.tags;
    $scope.tagNames = $scope.tags.map(function(t){ return t.name; });
    $scope.currentTag = {};
    var emptyTag = {
        _id : '',
        name : '',
        users : ''
    };


    /* --- scope functions --- */

    $scope.saveNote = function(note) {
        var isNewNote = false;

        // check note id : for new note temp note _id ..
        if(!note._id) {
            note._id = "temp-note-" + getRandomStr();
            isNewNote = true;   // set is new note
        }

        // verify tag
        var tagName;
        if(!note.tag || !note.tag.name || note.tag.name.trim() === '') {
            tagName = 'personal';   // default use personal
        } else {
            tagName = note.tag.name;
        }

        var tagObj = $scope.findOrCreateTag(tagName);
        note.tag = tagObj;

        // deadline related
        // TODO add by/on
        note.deadline = new Date(note.deadline);
        

        var inNote = angular.copy(note);
        inNote.isDirty = true;

        if(isNewNote) {
            $storage.notes.push(inNote);
        } else {
            // existing note update
            var idx = getNoteIndex(inNote._id);
            $storage.notes[idx] = inNote;
        }

        // Sync it with backend
        Note.save(inNote, function(response) {
            console.log(response);
        });

        $scope.resetNoteForm();
        return inNote;
    };

    function getNoteIndex(noteId) {
        for(var i = 0; i < $storage.notes.length; i++) {
            if(noteId === $storage.notes[i]._id) {
                return i;
            }
        }

        return -1;
    };

    $scope.editNote = function(note) {
        // Set the current note
        $scope.currentNote = angular.copy(note);
    };

    $scope.removeNote = function(note) {
        var index = $storage.notes.indexOf(note);
        $storage.notes.splice(index, 1);
    };

    $scope.resetNoteForm = function() {
        // copy emptyNote in to currentNote
        $scope.currentNote = angular.copy(emptyNote);
    };

    $scope.getTagNames = function() {
        return $storage.tags.map(function(t){
            return t.name;
        });
    };

    /**
     * Find tag by name or create one.
     */
    $scope.findOrCreateTag = function(tagName) {
        var tags = $storage.tags.filter(function(t){
            return t.name === tagName;
        });

        if(tags && tags.length > 0)
            return tags[0];

        // if not found return empty
        return $scope.saveTag({ 'name' : tagName });
    };

    /**
     * Get tag by id.
     * TODO: Not in use 
     */
    $scope.getTag = function(tagId) {
        var tags = $storage.tags.filter(function(t){
            return t._id === tagId;
        });

        // assumption atleast one result will be there
        if(tags && tags.length > 0)
            return tags[0];

        // this should never happen
        return $scope.emptyTag;
    };

    $scope.saveTag = function(tag) {
        var isNewTag = false;

        // check tag id : for new tag temp tag _id ..
        if(!tag._id) {
            tag._id = "temp-tag-" + getRandomStr();
            isNewTag = true;    // set is new tag
        }

        if(tag.users && tag.users != "") {
            var usrStr = tag.users;
            var users = usrStr.split(",").map(function(email){
                var emailStr = email.trim();
                if(!emailStr) return;

                // prepare user ..
                var user = {};
                user.email = emailStr;
                return user;
            }).filter(function(u) {
                return u;
            });

            tag.users = users;
        }

        var inTag = angular.copy(tag);
        inTag.isDirty = true;

        if(isNewTag) {
            $storage.tags.push(inTag);
        } else {
            // existing tag update
            var idx = getTagIndex(inTag._id);
            $storage.tags[idx] = inTag;
        }

        // Sync it with backend
        Note.save(inTag, function(response) {
            console.log(response);
        });

        $scope.resetTagForm();
        return inTag;
    };

    function getTagIndex(tagId) {
        for(var i = 0; i < $storage.tags.length; i++) {
            if(tagId === $storage.tags[i]._id) {
                return i;
            }
        }

        return -1;
    };

    $scope.editTag = function(tag) {
        // Set the current tag
        $scope.currentTag = angular.copy(tag);
    };

    $scope.resetTagForm = function() {
        // copy emptyTag in to currentTag
        $scope.currentTag = angular.copy(emptyTag);
    };

    $scope.prepareTagUsersStr = function(users) {
        if(!users) 
            return '';

        var emailStr = users.map(function(u) { return u.email; }).join(', ');
        return emailStr;
    };


    /* --- utility functions --- */

    function getRandomStr() {
        return Math.random().toString(36).slice(2);
    };




    
    /* ----------------------------- Auth section ------------------------------  */

    
    
    
    /**
     * Checks the access headers to verify authentication
     */
    $scope.isAuthenticated = function() {

//         console.log("Header Access Token : " + $localStorage.header_accessToken);
//         console.log("Header Access ID : " + $localStorage.header_accessId);

        var isAuthTokenSet = 
            $localStorage.header_accessToken && ($localStorage.header_accessToken != null) 
            && $localStorage.header_accessId && ($localStorage.header_accessId != null);

//         console.log("is authenticated : " + isAuthTokenSet);

        return isAuthTokenSet || false;
    };
    
    
    var param_accessToken_name = 'access_token';
    var param_accessId_name = 'access_id';
    
    /**
     * Set the access details in the local storage
     * TODO should be global
     */
    function setAccessDetailsInStorage(accessObj) {
        $localStorage.header_accessToken = accessObj[param_accessToken_name];
        $localStorage.header_accessId = accessObj[param_accessId_name];
    };

    $scope.signinUser = function(eUser) {

        Auth.signIn(eUser, function(res) {

            setAccessDetailsInStorage(res);
        }, function(err) {
//             console.log(err);
        });

    };

    $scope.signupUser = function(nUser) {

        Auth.signUp(nUser, function(res) {
            // set user
            $localStorage.user = res.user;

            setAccessDetailsInStorage(res);
        }, function(err) {
//             console.log(err);
        });

    };

    $scope.signoutUser = function() {

        var user = {};
        Auth.signOut(user, function(res) {
            console.log(res);
            setAccessDetailsInStorage({});
        }, function(err) {
//             console.log(err);
        });

    };


});


/* --- Angular Filters --- */

app.filter('filterByTag', function () {
    return function(notes, notesFilters){

        var tagNames = Object.keys(notesFilters).filter(function(k){
            return notesFilters[k]; 
        });

        // Quick validation
        if((tagNames.indexOf("all") != -1) || !notes) {
            return notes;
        }

        // Apply filter
        var filteredNotes = notes.filter(function(n) {
            return tagNames.indexOf(n.tag.name) != -1;
        });

        return filteredNotes;
    }
});