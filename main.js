var serviceList = {};

function startFunction(service){
	
    		
  

  
}

function stopFunction(service){
	

}

function createStopButton(nodeValue,localKey){
	var stopButton = document.createElement("BUTTON");
    stopButton.innerHTML = "Stop";
    stopButton.setAttribute("id",localKey.concat("Stop"));
    var tmpKey = "'"+localKey+"'";
    var stopFunction = "stopFunction("+tmpKey+")";
    stopButton.setAttribute("onclick",stopFunction);
    nodeValue.appendChild(stopButton);
}

function createStartButton(nodeValue,localKey){
	var startButton = document.createElement("BUTTON");
    startButton.innerHTML = "Start";
    startButton.setAttribute("id",localKey.concat("Start"));
    var tmpKey = "'"+localKey+"'";
    var startFunction = "startFunction("+tmpKey+")";
    startButton.setAttribute("onclick",startFunction);
    nodeValue.appendChild(startButton);
}

function updateButton(serviceId,buttonText){
	 document.getElementById(serviceId).value = buttonText;
}

function removeNode(serviceId){
	 document.getElementById(serviceId).remove();
}

function createNode(key){
	var node = document.createElement("LI");
    var textnode = document.createTextNode(key);
    node.appendChild(textnode);
    node.setAttribute("id",key);
    return node;
}

function checkIfServiceExists(serviceName,serviceValue){
	var checkMap = {};
	var isPresent = false;
	for(var element in serviceList){
		if(element === serviceName){
			isPresent = true;
			break;
		}
	}
	if(!isPresent){
		checkMap["exists"] = "doesNotExists";
		serviceList[serviceName] = serviceValue;
	}
	else{
		if(serviceValue === serviceList[serviceName]){
			checkMap["exists"] = "notModified";
		}
		else{
			checkMap["exists"] = "modified";
			serviceList[serviceName] = serviceValue;
		}
	}
    return checkMap;
}


function loadDoc() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
    	var obj = JSON.parse(xhttp.response);
    	for (var key in obj) {
  			if (obj.hasOwnProperty(key)) {

    			var value = obj[key];
    			//var node = null;
    			var serviceCheck = checkIfServiceExists(key,value);
    			if(serviceCheck["exists"] === "doesNotExists"){
    			// Adding Start Button and some function  for the button acction dynamically
    				var node = createNode(key);
    				if(!value){
    					createStartButton(node,key);
    			
    			// Adding Stop Button and some function  for the button acction dynamically
    					createStopButton(node,key);
    				}
    				else{
    					createStopButton(node,key);
    				}
    				document.getElementById("myList").appendChild(node);
    				
    		}
    			else if(serviceCheck["exists"] === "modified"){
    				//if value is true before it is false and we had two buttons "Stop" and "Start" so now remove Start button.
    					if(value == true){
    						removeNode(key+"Start");
    					}
    				// if value is false before it is true means we had only stop button, now we have to add one more button Stop
    					else{
    						removeNode(key);
    						var node = createNode(key);
    						createStartButton(node,key);
    						createStopButton(node,key);
    						document.getElementById("myList").appendChild(node);
    					}
    			}
    			else {
    				continue;
    			}
  }
}
    }
  };
  xhttp.open("GET", "http://localhost:9090/service_list", true);
  xhttp.send();
}
