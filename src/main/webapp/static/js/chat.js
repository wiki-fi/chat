$(document).ready(function() {

    var list = document.getElementById("msgs");
    var sendButton = document.getElementById("send");
    var msg = $("#msg");
    var messageList = [];

    sendButton.addEventListener("click",send);

    function send()   {

        fetch("/chat?action=send",
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json; charset=utf-8'
                },
                method: "POST",
                body: JSON.stringify({timestamp: $.now(), text: msg.val(), sender: {id:0,name: "alice"}})
            })
            .then(function(response){
                if (response.ok) { return response.json();}
                return Promise.reject(new HttpError('HTTP error: ' + response.status));
            })
            .then( function(data){
                var renderedList = "";
                $.each(data, function (i, m) {
                    renderedList = renderedList + "<li>" + m.timestamp+ " " +m.sender.name + ": " + m.text+ "</li>\n";
                });
                msg.val('');
                list.innerHTML = renderedList;

                })
            .catch(function(res){ console.log(res) });

    }

});
