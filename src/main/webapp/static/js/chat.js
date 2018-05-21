$(document).ready(function() {

    var list = document.getElementById("msgs");
    var sendButton = document.getElementById("send");
    var msg = $("#msg");

    sendButton.addEventListener("click",send);


    var msg = JSON.parse(event.data);

    list.append("<li>" +msg.text + "</li>")



    function send()   {

        $.ajax({
            url: '/chat',
            processData: false,
            type: "POST",
            data: str, // send the string directly
            success: function(){
                //alert('OK!');
            },
            error: function(response) {
                alert(response);
            }
        });
    }

});
