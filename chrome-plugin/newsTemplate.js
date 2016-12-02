function loadDataNode(userKey) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        var obj = JSON.parse(xhttp.response);
        for (var key in obj){
            if(key === "url"){
                var node = createLinkNode(obj[key]);
                document.body.appendChild(node);
            }
            if(key == "title"){
                var node = createParaNode(obj[key]);
                document.body.appendChild(node);
            }
        }
    
    }
  };
  var url = "http://localhost:9090/" + userKey + "/dataInfo";
  xhttp.open("GET", url, true);
  xhttp.send();
}

function createLinkNode(link){
    var node = document.createElement('a');
    node.href  =  link;
    node.innerHTML = "Article Link";
    return node;
}

function createParaNode(content){
    var node = document.createElement('p');
    node.innerHTML = content;
    return node;
}

document.addEventListener('DOMContentLoaded', function () {
    //TODO : need to pass user specific key to the function loadDataNode...
  
  loadDataNode("manojsss");
});