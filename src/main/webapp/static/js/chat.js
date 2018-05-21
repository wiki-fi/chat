$(document).ready(function() {

    var list = document.getElementById("msgs");
    var sendButton = document.getElementById("send");
    var msg = $("#msg");
    var messageList = [];

    sendButton.addEventListener("click",send);



    function send()   {

        $.ajax({
            url: '/chat?action=send',
            type: "POST",
            contentType: "application/json; charset=utf-8",
            // contentType: "plain/text; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({timestamp: $.now(), text: msg.val(), sender: {id:0,name: "alice"}}), // send the string directly
            success: function(data){
                print(data);
                // messageList = $.merge([], data);
                // console.log(data[0]);
                // //alert($.parseJSON(data));
                // alert(JSON.stringify(messageList, null, 2));
            },
            failure: function(errMsg) {alert(errMsg);}
            // error: function(response) {
            //     alert(response);
            // }
        });

        function print(data) {
            var list_of_msgs = "";
            $.each(data, function (i, m) {
                list_of_msgs= list_of_msgs + "<li>" + m.sender.name + ": " + m.text+ "</li>\n";
            });
            msg.val('');

            list.innerHTML = list_of_msgs;

        }
    }

});
