if (!window.WebSocket) {
    alert("This won't work in your browser. Try Chrome or a gooder version of Safari.");
} else {
    function connectWs() {
        if (!window.ws || window.ws.readyState != WebSocket.OPEN) {
            window.ws = new WebSocket("ws://"+location.host+"/topics/topic/websocket");

            window.ws.onopen = function(event) {
                console.log("WebSocket opened!");
            };
            window.ws.onmessage = function(event) {
                console.log(event);
                $('#messages').prepend("<div class='list-group-item'>" + event.data + "</div>");
            };
            window.ws.onclose = function(event) {
                var timer = setTimeout(function() {
                    console.log("Retrying connection...");
                    connectWs();
                    if (window.ws.readyState == WebSocket.OPEN) {
                        clearTimeout(timer);
                    }
                }, 1000);
            };
        }
    }

    connectWs();
}
