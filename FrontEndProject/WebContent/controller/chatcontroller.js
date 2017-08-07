/**
 * 
 */
app.controller('ChatCtrl', ['$rootScope' ,'$scope', 'socket', function($rootScope ,$scope, socket) {
    alert('entering chat controller')
    $scope.chats = [];
    $scope.stompClient = socket.stompClient;
    $scope.persons=[]
    $scope.$on('sockConnected', function(event, frame) {
    	alert('sockconnected')
        $scope.userName=$rootScope.currentPerson.username;
        $scope.stompClient.subscribe("/topic/join", function(message) {
        	
            person = JSON.parse(message.body);
            console.log(person)
           
            if(person != $scope.userName && $.inArray(person, $scope.persons) == -1) {
                $scope.addPerson(person);
                $scope.latestPerson = person;
                $scope.$apply();
                $('#joinedChat').fadeIn(100).delay(2000).fadeOut(200);
            }
            
        });
        
  
        $scope.stompClient.subscribe('/app/join/'+$scope.userName, function(message) {
        
            $scope.persons = JSON.parse(message.body);
        	
            $scope.$apply();
        });
        
    });

    $scope.sendMessage = function(chat) {
        chat.from = $scope.userName;
      
        $scope.stompClient.send("/app/chat", {}, JSON.stringify(chat));
        $rootScope.$broadcast('sendingChat', chat);
        $scope.chat.message = '';

    };

    $scope.capitalize = function(str) {
        return str.charAt(0).toUpperCase() + str.slice(1);
    };
 
    $scope.addPerson = function(person) {
        $scope.persons.push(person);
        $scope.$apply();
    };
 
    
    
    
    
    
    
    $scope.$on('sockConnected', function(event, frame) {
        $scope.userName=$rootScope.currentPerson.username;
  
        $scope.person=$rootScope.currentPerson.username;
        /**
        $scope.stompClient.subscribe("/queue/chats" + queueSuffix, function(message) {
        	alert('QUEUE SUFFIX ' + queueSuffix)
        	alert(message)
            $scope.processIncomingMessage(message, false);
        });
    */
        $scope.stompClient.subscribe( "/queue/chats/"+$scope.userName, function(message) {
        	
            $scope.processIncomingMessage(message, false);
        });
        
        
        $scope.stompClient.subscribe("/queue/chats", function(message) {
        	
            $scope.processIncomingMessage(message, true);
        });
        
        
    });

    $scope.$on('sendingChat', function(event, sentChat) {
        chat = angular.copy(sentChat);
        chat.from = 'Me';
        chat.direction = 'outgoing';
        $scope.addChat(chat);
    });

    $scope.processIncomingMessage = function(message, isBroadcast) {
        message = JSON.parse(message.body);
        message.direction = 'incoming';
        if(message.from != $scope.userName) {
        	$scope.addChat(message);
            $scope.$apply(); // since inside subscribe closure
        }
    };

 
    $scope.addChat = function(chat) {
        $scope.chats.push(chat);
    };
 
 
}]);