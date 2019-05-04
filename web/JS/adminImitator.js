function sendChangedData(i, actionName){
    let servoNum = document.getElementById("ardInd" + i).value;
    let leapMin = document.getElementById("leapMin" + i).value;
    let leapMax = document.getElementById("leapMax" + i).value;
    let servoD = document.getElementById("servoD" + i).innerHTML;
    let servoMin = document.getElementById("servoMin" + i).value;
    let servoMax = document.getElementById("servoMax" + i).value;
    let avail = document.getElementById("avail"+i).style.backgroundColor;
    let flag = true;

    if(isNaN(servoNum)){
        changeStyleColor("ardInd" + i, "#ff3a3a");
        flag = false;
    }else{
        changeStyleColor("ardInd" + i, "#000000");
    }
    if(isNaN(leapMin)){
        changeStyleColor("leapMin" + i, "#ff3a3a");
        flag = false;
    }else{
        changeStyleColor("leapMin" + i, "#000000");
    }
    if(isNaN(leapMax)){
        changeStyleColor("leapMax" + i, "#ff3a3a");
        flag = false;
    }else{
        changeStyleColor("leapMax" + i, "#000000");
    }
    if(isNaN(servoMin)){
        changeStyleColor("servoMin" + i, "#ff3a3a");
        flag = false;
    }else{
        changeStyleColor("servoMin" + i, "#000000");
    }
    if(isNaN(servoMax)){
        changeStyleColor("servoMax" + i, "#ff3a3a");
        flag = false;
    }else {
        changeStyleColor("servoMax" + i, "#000000");
    }

    if(flag){
        let servoDirection;
        if(servoD === "Clockwise"){
            servoDirection = 1;
        }else{
            servoDirection = 0;
        }

        let availability;
        if(document.getElementById("avail"+i).style.backgroundColor === "hotpink"){
            availability = 0;
        }else{
            availability = 1;
        }

        $.ajax({
            url: "imitatorChange",
            data: {name: actionName, servoNum: servoNum, leapMin: leapMin, leapMax: leapMax,
                servoDirection: servoDirection, servoMin: servoMin, servoMax: servoMax,
                availability: availability},
            cache: false
        });
    }
}

function changeStyleColor(name, color) {
    document.getElementById(name).style.color = color;
}

function changeServoDirection(i) {
    if(document.getElementById("servoD"+i).innerHTML === "Clockwise"){
        document.getElementById("servoD"+i).innerHTML = "Counter-clockwise";
    }else{
        document.getElementById("servoD"+i).innerHTML = "Clockwise"
    }
}

function changeAvailability(i) {
    if (document.getElementById("avail"+i).style.backgroundColor === "hotpink"){
        document.getElementById("avail"+i).style.backgroundColor = "#2bff36";
    }else{
        document.getElementById("avail"+i).style.backgroundColor = "hotpink";
    }
}