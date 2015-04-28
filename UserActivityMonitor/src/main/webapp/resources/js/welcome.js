jQuery(document).ready(function($) {
$("#testdiv").mousemove(function( event ) {
        var pageCoords = "( " + event.pageX + ", " + event.pageY + " )";
        $( "span:first" ).text( "( X, Y ) : " + pageCoords );
        $.ajax({
            url : "/UserActivityMonitor/"+event.pageX+"/"+event.pageY+"/",
            type: "POST",
            success: function(data, textStatus, jqXHR)
            {
                //data - response from server
            },
            error: function (jqXHR, textStatus, errorThrown)
            {

            }
        });
    });


});
