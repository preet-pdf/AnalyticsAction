document.getElementById("dropdown1").addEventListener("change", function() {
    sendAuditEvent("dropdown1_clicked", this.value);
});

document.getElementById("dropdown2").addEventListener("change", function() {
    sendAuditEvent("dropdown2_clicked", this.value);
});

document.getElementById("inputField").addEventListener("input", function() {
    sendAuditEvent("input_data_entered", this.value);
});

document.getElementById("button1").addEventListener("click", function() {
    sendAuditEvent("button_clicked1", "Button 1");
});

document.getElementById("button2").addEventListener("click", function() {
    sendAuditEvent("button_clicked2", "Button 2");
});

document.getElementById("invalid_button").addEventListener("click", function() {
    sendAuditEvent("invalid_button", "Button 2");
});

document.getElementById("buttonContainer").addEventListener("dblclick", function(event) {
    if (event.target.tagName === 'BUTTON') {
        sendAuditEvent("button_double_clicked", event.target.textContent);
    }
});


function sendAuditEvent(eventType, eventData) {
    var isoDateString = new Date().toISOString();
    console.log(isoDateString);
    // Send audit event to backend
    fetch('http://localhost:8080/audit/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ eventType: eventType, eventData: eventData, eventTime: isoDateString })
    });
}

function trackStartTime() {
    localStorage.setItem('startTime', Date.now());
}

function displayTimeSpent() {
    const startTime = localStorage.getItem('startTime');
    if (startTime) {
        const endTime = Date.now();
        const elapsedTime = endTime - parseInt(startTime);
        const seconds = Math.floor(elapsedTime / 1000);
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;
        console.log(`You spent ${minutes} minutes and ${remainingSeconds} seconds on this page.`);
        sendAuditEvent("spent_time", minutes);
    } else {
        console.log('No start time found.');
    }
}

window.addEventListener('beforeunload', function() {
    displayTimeSpent();
});

window.addEventListener('load', trackStartTime);