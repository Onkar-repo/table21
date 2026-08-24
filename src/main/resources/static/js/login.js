async function SendData() {
    try {
        const userObject = {

            userEmail: document.getElementById('reg_email').value,
            userPassword: document.getElementById('reg_password').value,
        };
        const userObjectJSON = JSON.stringify(userObject);
        const mhb = {
            method: 'POST',
            headers: { 'Content-Type': 'Application/json', 'Access-Control-Allow-Origin': '*' },
            body: userObjectJSON
        };
        const loginResponse = await fetch("http://localhost:8080/login", mhb);
        if (!loginResponse.ok)
            throw new Error(loginResponse.status + " occured.");
      //  const textResponse = await loginResponse.text();

    }
    catch (error) {
        console.log(error);
    }

}

function validateLogIn() {

    const ue = document.getElementById('reg_email').value;
    const up = document.getElementById('reg_password').value;

    if (ue === "" || up === "") {
        showAlert("Validation", "All fields are compulsory. Fill up all.");
        errName = 3;
        //document.getElementById('reg_username').focus();
    }
    else if (!(document.getElementById('reg_email').checkValidity() && ue.split("@")[1].includes("."))) {
        showAlert("Validation", "Email id format is incorrect.");
        errName = 4;
    }
    else {
        SendData();
    }

}


/* Alert Box Script */

function showAlert(title, message) {
    if (title) document.getElementById('alertTitle').innerText = title;
    if (message) document.getElementById('alertMessage').innerText = message;
    const overlay = document.getElementById('customAlertOverlay');
    overlay.classList.add('active');
    document.getElementById('alertOkBtn').focus();
}

function closeAlert() {
    const overlay = document.getElementById('customAlertOverlay');
    overlay.classList.remove('active');
            document.getElementById('reg_email').focus();
}
