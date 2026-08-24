let errName;
function clean() {
    document.getElementById("reg_username").value = "";
    document.getElementById("reg_email").value = "";
    document.getElementById("reg_password").value = "";
    document.getElementById("reg_confirm_password").value = "";
    document.getElementById("reg_username").focus();
}

async function SendData() {
    try {
        const userObject = {

            userName: document.getElementById('reg_username').value,
            userEmail: document.getElementById('reg_email').value,
            userPassword: document.getElementById('reg_password').value,
        };
        const userObjectJSON = JSON.stringify(userObject);
        const mhb = {
            method: 'POST',
            headers: { 'Content-Type': 'Application/json', 'Access-Control-Allow-Origin': '*' },
            body: userObjectJSON
        };
        const signupResponse = await fetch("http://localhost:8080/signup", mhb);
        if (!signupResponse.ok)
            throw new Error(signupResponse.status + " occured.");
        const textResponse = await signupResponse.text();
        switch (textResponse) {
            case "saved":
                showAlert("Success", "User registered successfully. Click login link to begin.");
                errName = 1;
                break;
            case "exists":
                showAlert("Failure", "User with " + userObject.userEmail + " already exists.");
                errName = 2;
        }

    }
    catch (error) {
        console.log(error);
    }

}

function validateSignUp() {

    const un = document.getElementById('reg_username').value;
    const ue = document.getElementById('reg_email').value;
    const up = document.getElementById('reg_password').value;
    const ucp = document.getElementById('reg_confirm_password').value;

    if (un === "" || ue === "" || up === "" || ucp === "") {
        showAlert("Validation", "All fields are compulsory. Fill up all.");
        errName = 3;
        //document.getElementById('reg_username').focus();
    }
    else if (!(document.getElementById('reg_email').checkValidity() && ue.split("@")[1].includes("."))) {
        showAlert("Validation", "Email id format is incorrect.");
        errName = 4;
    }
    else if (up !== ucp) {

        showAlert("Validation", "Password confirmation does not match.");
        errName = 5;
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
    switch (errName) {
        case 1:
            clean();
            break;
        case 2:
            document.getElementById('reg_email').focus();
            break;
        case 3:
            document.getElementById('reg_username').focus();
            break;
        case 4:
            document.getElementById('reg_email').focus();
            break;
        case 5:
            document.getElementById('reg_confirm_password').focus();
    }
    errName = 0;
}
