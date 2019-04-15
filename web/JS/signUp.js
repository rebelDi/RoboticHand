var flag = false;

var check = function() {
    var password = document.getElementById('password').value;
    if (password.length === 0) {
        document.getElementById("messagePass").innerHTML = "";
    }

    // Create an array and push all possible values that you want in password
    var matchedCase = [];
    matchedCase.push("[A-Z]");      // Uppercase Alphabet
    matchedCase.push("[0-9]");      // Numbers
    matchedCase.push("[a-z]");     // Lowercase Alphabet

    // Check the conditions
    var ctr = 0;
    for (var i = 0; i < matchedCase.length; i++) {
        if (new RegExp(matchedCase[i]).test(password)) {
            ctr++;
        }
    }
    // Display it
    var color = "";
    var strength = "";
    switch (ctr) {
        case 0:
        case 1:
        case 2:
            strength = "Very Weak";
            color = "red";
            break;
        case 3:
            strength = "Medium";
            color = "orange";
            break;
        case 4:
            strength = "Strong";
            color = "green";
            break;
    }
    document.getElementById("messagePass").innerHTML = strength;
    document.getElementById("messagePass").style.color = color;

    if(document.getElementById("messagePass").style.color !== "red"){
        flag = true;
    }else{
        flag = false;
    }

    if(document.getElementById('password').value.length !== 0 && document.getElementById('confirmPassword').value.length !== 0) {
        if (document.getElementById('password').value ===
            document.getElementById('confirmPassword').value) {
            document.getElementById('messageConf').style.color = 'green';
            document.getElementById('messageConf').innerHTML = 'Passwords are matching';
        } else {
            document.getElementById('messageConf').style.color = 'red';
            document.getElementById('messageConf').innerHTML = 'Passwords are not matching';
        }
    }
}

function mainCheck() {
    if(!flag || document.getElementById('userName').value.length !== 0
        || document.getElementById('userRName').value.length !== 0
        || document.getElementById('userSurname').value.length !== 0
        || document.getElementById('userQuest').value.length !== 0){
        alert("Check all field, something is unacceptable!");
    }
}