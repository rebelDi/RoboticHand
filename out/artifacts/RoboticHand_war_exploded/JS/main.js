// $(document).on("click", "#checkConnection", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
//     $.get("checkConnection", function(responseText) {   // Execute Ajax GET request on URL of "checkConnection" and execute the following function with Ajax response text...
//         $("#response").text(responseText);           // Locate HTML DOM element with ID "response" and set its text content with the response text.
//         $("#response").css("visibility", "visible");
//         if(responseText === "Connected"){
//             $("#response").css("background-color", "mediumspringgreen");
//         }else{
//             $("#response").css("background-color", "#fc5355");
//         }
//     });
// });

function changeAvailability(result, i){
    if(result === 1) {
        if (document.getElementById("flag"+i).value === "0"){
            document.getElementById(i).style.backgroundColor = "#2bff36";
            document.getElementById("flag"+i).value = "1";
        }else{
            document.getElementById(i).style.backgroundColor = "#ff3a3a";
            document.getElementById("flag"+i).value = "0";
        }
    }
}

function popUpShow() {
    const popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
}

function popUpHide() {
    const popup = document.getElementById("myPopup");
    popup.classList.toggle("hide");
}

function changeRights(login, right) {
    alert(login);
    document.getElementById(login).value = right;
}

function showQuestion(questionNum){
    document.getElementById("question").value = document.getElementById("Q" + questionNum).value;
    document.getElementById("answer").value = document.getElementById("A" + questionNum).value;
    document.getElementById("userLogin").value = document.getElementById("U" + questionNum).value;
    document.getElementById("userQ").value = document.getElementById("Q" + questionNum).value;
}

function showQuestionForUser(questionNum){
    document.getElementById("question").value = document.getElementById("Q" + questionNum).value;
    document.getElementById("answer").value = document.getElementById("A" + questionNum).value;
    document.getElementById("userLogin").value = document.getElementById("U" + questionNum).value;
}