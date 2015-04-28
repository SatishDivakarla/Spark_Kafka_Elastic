jQuery(document).ready(function($) {
setInterval(function() {
      $.ajax({
        url: '/UserActivityMonitor/viewactivity/',
        success: function(data) {
        $('#div1').css("background-color", "#0099cc");
        $('#div2').css("background-color", "#0099cc");
        $('#div3').css("background-color", "#0099cc");
        $('#div4').css("background-color", "#0099cc");
        $('#div'+data).css("background-color", "rgb(178, 39, 39)");

        },
        complete: function() {
        }
      });
}, 5000);

});