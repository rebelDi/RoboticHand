$(document).on("click", "#checkConnection", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
    $.get("checkConnection", function(responseText) {   // Execute Ajax GET request on URL of "checkConnection" and execute the following function with Ajax response text...
        $("#response").text(responseText);           // Locate HTML DOM element with ID "response" and set its text content with the response text.
        $("#response").css("visibility", "visible");
        if(responseText === "Connected"){
            $("#response").css("background-color", "mediumspringgreen");
        }else{
            $("#response").css("background-color", "#fc5355");
        }
    });
});