// Store frame for motion functions
var previousFrame = null;
var paused = true;
var a, b;
// Setup Leap loop with frame callback function
var controllerOptions = {enableGestures: true};
var actions = ["PalmX", "PalmY", "Hand Pitch", "Hand Roll", "Thumb", "Index finger", "Middle finger", "Ring finger", "Pinky finger"];

Leap.loop(controllerOptions, function(frame) {
    if (paused) {
        return; // Skip this update
    }

    // // Display Hand object data
    if (frame.hands.length > 0) {
        for (var i = 0; i < frame.hands.length; i++) {
            var hand = frame.hands[i];
            var handId;

            if(hand.type === "left") {
                handId = hand.id;

                if(document.getElementById("flag" + String(actions.indexOf("PalmX"))).value === "1") {
                    document.getElementById("PalmX").innerText = hand.palmPosition[0];
                }else{
                    document.getElementById("PalmX").innerText = "";
                }

                if(document.getElementById("flag" + String(actions.indexOf("PalmY"))).value === "1") {
                    document.getElementById("PalmY").innerText = hand.palmPosition[1];
                }else{
                    document.getElementById("PalmY").innerText = "";
                }
            }
        }
    }

    // Display Pointable (finger and tool) object data
    if (frame.pointables.length > 0) {
        var fingerTypeMap = ["Thumb", "Index finger", "Middle finger", "Ring finger", "Pinky finger"];
        var boneTypeMap = ["Metacarpal", "Proximal phalanx", "Intermediate phalanx", "Distal phalanx"];
        for (var i = 0; i < frame.pointables.length; i++) {
            var pointable = frame.pointables[i];

            if (!pointable.tool) {
                if(pointable.handId === handId) {
                    pointable.bones.forEach(function (bone) {
                        if(fingerTypeMap[pointable.type] === "Thumb") {
                            if (boneTypeMap[bone.type] === "Intermediate phalanx") {
                                a = bone.direction();
                            }else {
                                if (boneTypeMap[bone.type] === "Proximal phalanx") {
                                    b = bone.direction();
                                }
                            }
                        }else{
                            if (boneTypeMap[bone.type] === "Metacarpal") {
                                a = bone.direction();
                            } else {
                                if (boneTypeMap[bone.type] === "Proximal phalanx") {
                                    b = bone.direction();
                                }
                            }
                        }
                    });
                    var crossProduct = Leap.vec3.create();
                    Leap.vec3.cross(crossProduct, a, b);
                    crossProduct = Math.asin(getMagnitude(crossProduct)) * (180 / Math.PI);
                    if(document.getElementById("flag" + String(actions.indexOf(fingerTypeMap[pointable.type]))).value === "1") {
                        document.getElementById(String(fingerTypeMap[pointable.type])).innerText = crossProduct;
                    }else{
                        document.getElementById(String(fingerTypeMap[pointable.type])).innerText = "";
                    }
                }
            }
        }
    }

    // Store frame for motion functions
    previousFrame = frame;
})

function getMagnitude(v1) {
    return Math.sqrt(v1[0] * v1[0] + v1[1] * v1[1] + v1[2] * v1[2]);
}

function vectorToString(vector, digits) {
    if (typeof digits === "undefined") {
        digits = 1;
    }
    return "(" + vector[0].toFixed(digits) + ", "
        + vector[1].toFixed(digits) + ", "
        + vector[2].toFixed(digits) + ")";
}

function togglePause() {
    paused = !paused;

    if (paused) {
        document.getElementById("pause").innerText = "Resume";
        document.getElementById("pause").style.backgroundColor = "lightpink";
    } else {
        document.getElementById("pause").innerText = "Pause";
        document.getElementById("pause").style.backgroundColor = "aquamarine";
    }
}