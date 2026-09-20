let errNum;
async function SendData() {
	showLoading();
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
     // const loginResponse = await fetch("/login", mhb);
		const loginResponse = await fetch("/table21/login", mhb);
		hideLoading();        
		if (!loginResponse.ok){
			console.log(loginResponse.status + " : " + loginResponse.statusText); // temporary message
		}
		else{
			const textResponse = await loginResponse.text();
			console.log(textResponse);
			switch(textResponse){
				case "wrongCred":
					showAlert("Authentication","Invalid email or password. Sign up if not registered yet.");
					break;
				case "sessionStarted":
					console.log("/dashboard?"+ new URLSearchParams({userEmail:document.getElementById('reg_email').value}).toString());
	//				window.location.href = "/dashboard?"+ new URLSearchParams({userEmail:document.getElementById('reg_email').value}).toString();
					window.location.href = "/table21/dashboard?"+ new URLSearchParams({userEmail:document.getElementById('reg_email').value}).toString();
					break;
				case "error":
					showAlert("Error","Could not authenticate right now. Try after some time.");
			}
		}
     
      

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


function redirectToSignup(){
//		window.location.href="/signup";
  	window.location.href="/table21/signup";
}


/* Alert Box Script */

function showAlert(title, message) {
    if (title) document.getElementById('alertTitle').innerText = title;
    if (message) document.getElementById('alertMessage').innerText = message;
    const overlay = document.getElementById('customAlertOverlay');
    overlay.classList.add('active');
    setTimeout(()=>{document.getElementById("alertOkBtn").focus();},50);
}

function closeAlert() {
    const overlay = document.getElementById('customAlertOverlay');
    overlay.classList.remove('active');
            document.getElementById('reg_email').focus();
}


/* Yesno box script */

function showDialog() {
      const overlay = document.getElementById('customDialogOverlay');
      overlay.classList.add('active');
      // Auto-focus primary action for accessibility
     setTimeout(()=> {document.getElementById('customDialogOverlay').focus();document.getElementById('dialogYesBtn').focus()},100);
  }

  function closeDialog() {
      const overlay = document.getElementById('customDialogOverlay');
      overlay.classList.remove('active');
	  document.getElementById('reg_email').focus();
  }

async function handleResponse(isYes) {
      closeDialog();
      if (isYes) {
		
		if(document.getElementById('reg_email').value===""){
			showAlert("Validation", "Email id required.");
			errNum = 1;
			return;
		}
		if (!(document.getElementById('reg_email').checkValidity() && document.getElementById('reg_email').value.split("@")[1].includes("."))){
			showAlert("Validation", "Email id format is incorrect.");
			errNum = 1;
			return;
		}
		showLoading();
		
	//	const url = new URL("/recover", window.location.origin);
		const url = new URL("/table21/recover", window.location.origin);
		url.search = new URLSearchParams({ billUser: document.getElementById('reg_email').value }).toString();
		const recoverResponse = await fetch(url);
		if (!recoverResponse.ok) {
		            console.log(recoverResponse.status + ": " + recoverResponse.statusText);
		            // display custom dialog error
		        }
		else{
			const msgResponse = await recoverResponse.text();
			hideLoading();
			showAlert("Information", msgResponse);
		}
		
		      
      } else {
		document.getElementById('reg_email').focus();
      }
  }
  
  
  
  
  
  
 

 /* Custom Loading Dialog script  */ 
  function showLoading() {
	
    document.getElementById('customLoadingOverlay').style.display = 'flex';
  } 
  function hideLoading() {
    document.getElementById('customLoadingOverlay').style.display = 'none';
  }