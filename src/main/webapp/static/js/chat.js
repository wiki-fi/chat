$(document).ready(function() {

    var list = document.getElementById("msgs");
    var sendButton = document.getElementById("send");
    var msg = $("#msg");
    var messageList = [];

    sendButton.addEventListener("click",send);



    function send()   {


        var message = {timestamp:1526862155795, text:"hello1",sender:{id:0,name:"alice"}};
        var json = JSON.stringify({timestamp:$.now(), text:"hello1",sender:{id:0,name:"alice"}});



        $.ajax({
            url: '/chat?action=send',
            type: "POST",
            contentType: "application/json; charset=utf-8",
            // contentType: "plain/text; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({timestamp:$.now(), text:msg.val(),sender:{id:0,name:"alice"}}), // send the string directly
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
            $.each(data, function (index,m) {
                list_of_msgs= list_of_msgs + "<li>" + JSON.stringify(m) + "</li>\n";
            });
            msg.val('');

            list.innerHTML = list_of_msgs;

        }
    }

});
