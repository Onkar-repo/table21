console.log("hello");
let errNum, actNum, noOrderYet;
let regItemsCart = [];
function loadUserName() {
    const querryString = window.location.search;
    const querryParams = new URLSearchParams(querryString);
    document.getElementById('ue').innerText = querryParams.get('userEmail');
}

function completeButtonHit() {

    if (noOrderYet) {
        showAlert("Validation", "Empty list. Can not complete the bill.");
        errNum = 1;
    }
    else {
        actNum = 4;
        showDialog("Sure to complete current bill ?");

    }
}


async function gotoEditItemPage(){	
		const url = new URL("http://localhost:8080/dashboard/edititempage");
		     url.search = new URLSearchParams({ billUser: document.getElementById('ue').innerText }).toString();
				 window.location.href = url;
}


async function gotoEnlistPage(){
	const url = new URL("http://localhost:8080/dashboard/enlistpage");
			     url.search = new URLSearchParams({ billUser: document.getElementById('ue').innerText }).toString();
					 window.location.href = url;
}


async function completeBillAndUpdate() {
    try {
        const url = new URL("http://localhost:8080/dashboard/completebill");
        url.search = new URLSearchParams({ billUser: document.getElementById('ue').innerText, billNumber: document.getElementById('num').innerText }).toString();
        console.log(url);


        const resultResponse = await fetch(url);
        if (!resultResponse.ok) {
            console.log(resultResponse.status + ": " + resultResponse.statusText);
            // display custom dialog error
        }
        else {
            const result = await resultResponse.text();
            // retrive items list and populate in combo box
            if (result === "done") {
                closeTable();
                showAlert("Information", "Completed! Now it is accessible from reports.");
                errNum = 6;
            }
            else {
                // display custom dialog error
                showAlert("Information", result);
                errNum = 7;
            }
        }

    }
    catch (error) {
        console.log(error);
    }
}

function printButtonHit() {
    actNum = 3;
    showDialog("Sure to generate bill page ?");
}

function loadPrintPage() {
    const url = new URL("http://localhost:8080/dashboard/printbill");
    url.search = new URLSearchParams({ billUser: document.getElementById('ue').innerText, billNumber: document.getElementById('num').innerText }).toString();
    console.log(url);
    window.location.href = url;
}

function clearButtonHit() {
    if (noOrderYet) {
        showAlert("Validation", "No items added yet.");
        errNum = 7;
    }
    else {
        actNum = 2;
        showDialog("Sure to clear all items ?");
    }
}

async function clearItemsAndUpdate() {
    try {
        const clearItemsPayload = {
            billUser: document.getElementById('ue').innerText,
            billTable: document.getElementById('tab').innerText,
            billNumber: document.getElementById('num').innerText,
            requestType: "ClearItems"
        };
        console.log(clearItemsPayload);
        const mhb = {
            method: 'POST',
            headers: { 'Content-Type': 'Application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify(clearItemsPayload)
        };
        const updatedItemsResponse = await fetch("http://localhost:8080/dashboard/clearitems", mhb);
        if (!updatedItemsResponse.ok) {
            console.log(updatedItemsResponse.status + ": " + updatedItemsResponse.statusText);
        }
        else {
            const result = await updatedItemsResponse.text();
            console.log(result);
            if (result === "done") {

                const parentList = document.getElementById("itemlist");
                parentList.replaceChildren();
                document.getElementById("total").innerText = "₹ 0";
                document.getElementById("itmcode").focus();
                noOrderYet = true;

            }
            else {
                showAlert("Information", result);
                errNum = 5;
            }
        }

    }
    catch (error) {
        console.log(error);
    }
}

function removeByRefHit(event) {
    if (event.key === "Enter") {
        if (document.getElementById('srno').value === "") {
            showAlert("Validation", "Ref can not be blank.");
            errNum = 3;
        }
        else {
            actNum = 1;
            showDialog("Sure to remove item by refrence: " + document.getElementById('srno').value + " ?");
        }
    }
}

async function removeItemAndUpdate() {
    try {

        const removeItemPayload = {
            billUser: document.getElementById('ue').innerText,
            billTable: document.getElementById('tab').innerText,
            itemPk: document.getElementById('srno').value,
            requestType: "RemoveItem"
        };
        console.log(removeItemPayload);
        const mhb = {
            method: 'POST',
            headers: { 'Content-Type': 'Application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify(removeItemPayload)
        };
        const updatedItemsResponse = await fetch("http://localhost:8080/dashboard/removeitem", mhb);
        if (!updatedItemsResponse.ok) {
            console.log(updatedItemsResponse.status + ": " + updatedItemsResponse.statusText);
        }
        else {
            const itemList = await updatedItemsResponse.json();
            console.log(itemList);
            if (itemList[0].message == null) {
                const parentList = document.getElementById("itemlist");
                parentList.replaceChildren();
                let tot = 0;
                for (let i = 0;i < itemList.length;i++) {
                    const rowDiv = document.createElement("div");
                    rowDiv.className = "bill-row";
                    //rowDiv.id = itemList[i].;
                    const span1 = document.createElement("span");
                    span1.textContent = itemList[i].serial;
                    const span2 = document.createElement("span");
                    span2.textContent = itemList[i].quantity;
                    const span3 = document.createElement("span");
                    span3.textContent = itemList[i].description;
                    const span4 = document.createElement("span");
                    span4.style.textAlign = "right";
                    span4.textContent = itemList[i].amount;
                    tot += Number(itemList[i].amount);
                    rowDiv.appendChild(span1);
                    rowDiv.appendChild(span2);
                    rowDiv.appendChild(span3);
                    rowDiv.appendChild(span4);
                    parentList.appendChild(rowDiv);
                }
                document.getElementById("total").innerText = "₹ " + tot;
                document.getElementById("srno").value = "";
                document.getElementById("itmcode").focus();
                if (tot === 0) noOrderYet = true;
            }
            else {
                errNum = 2;
                showAlert("Information", itemList[0].message);
            }

        }
    }
    catch (error) {
        console.log(error);
    }
}

async function addItemAndUpdate(event) {
    if (event.key === "Enter") {
        try {
            console.log("entered in addItem method");
            const addItemPayload = {
                billUser: document.getElementById('ue').innerText,
                billTable: document.getElementById('tab').innerText,
                itemCode: document.getElementById('itmcode').value,
                itemName: document.getElementById('itmlist').options[document.getElementById('itmlist').selectedIndex].text.split(":")[1],
                itemQuantity: document.getElementById('qty').value,
                requestType: "AddItem"
            };
            console.log(addItemPayload);
            const mhb = {
                method: 'POST',
                headers: { 'Content-Type': 'Application/json', 'Access-Control-Allow-Origin': '*' },
                body: JSON.stringify(addItemPayload)
            };
            console.log(mhb);
            const updatedItemsResponse = await fetch("http://localhost:8080/dashboard/additem", mhb);
            if (!updatedItemsResponse.ok) {
                console.log(updatedItemsResponse.status + ": " + updatedItemsResponse.statusText);
            }
            else {
                const itemList = await updatedItemsResponse.json();
                if (itemList[0].message == null) {
                    const parentList = document.getElementById("itemlist");
                    parentList.replaceChildren();
                    let tot = 0;
                    for (let i = 0;i < itemList.length;i++) {
                        const rowDiv = document.createElement("div");
                        rowDiv.className = "bill-row";
                        //rowDiv.id = itemList[i].;
                        const span1 = document.createElement("span");
                        span1.textContent = itemList[i].serial;
                        const span2 = document.createElement("span");
                        span2.textContent = itemList[i].quantity;
                        const span3 = document.createElement("span");
                        span3.textContent = itemList[i].description;
                        const span4 = document.createElement("span");
                        span4.style.textAlign = "right";
                        span4.textContent = itemList[i].amount;
                        tot += Number(itemList[i].amount);
                        rowDiv.appendChild(span1);
                        rowDiv.appendChild(span2);
                        rowDiv.appendChild(span3);
                        rowDiv.appendChild(span4);
                        parentList.appendChild(rowDiv);
                    }
                    document.getElementById("total").innerText = "₹ " + tot;
                    document.getElementById("itmcode").focus();
                    noOrderYet = false;
                }
                else {
                    errNum = 4;
                    showAlert("Information", itemList[0].message);
                }
            }
        }
        catch (err) {
            console.log(err);
        }
    }
}

function getItemCodeFromName() {
    if (document.getElementById("itmcode").value !== "Select an item...") {
        document.getElementById("itmcode").value = document.getElementById("itmlist").value.split(":")[0];
        //document.getElementById("qty").focus();
    }
}

function selectItemNameFromCode(event) {
    if (event.key === "Enter") {
        document.getElementById("itmlist").value = document.getElementById("itmcode").value;
        if (document.getElementById("itmlist").value !== document.getElementById("itmcode").value) {
            document.getElementById("itmlist").value = "";
            showAlert("Validation", "Item code does not exist.");
            errNum = 1;
        }
    }
}
async function loaduserItems() {
    try {
        const url = new URL("http://localhost:8080/dashboard/loaditems");
        url.search = new URLSearchParams({ billUser: document.getElementById('ue').innerText }).toString();
        const itemsListResponse = await fetch(url);
        if (!itemsListResponse.ok) {
            console.log(itemsListResponse.status + ": " + itemsListResponse.statusText);
            // display custom dialog error
        }
        else {
            const listOfItems = await itemsListResponse.json();
            // retrive items list and populate in combo box
            if (listOfItems.message === "Requested without authentication.") {
                // display custom dialog error
            }
            else {
                dropdown = document.getElementById("itmlist");
                dropdown.innerHTML = "<option value=''>Select an item...</option>";

                for (let i = 0;i < listOfItems.length;i++) {
                    const temp = document.createElement("option");
                    temp.value = listOfItems[i].itemCode/* + ":" + listOfItems[i].itemName*/;
                    temp.textContent = listOfItems[i].itemCode + ":" + listOfItems[i].itemName;
                    dropdown.appendChild(temp);
                }
            }
        }
    }
    catch (err) {
        console.log(err);
    }
}


async function loadTable(tableNumber) {

    try {
        const loadTablePayload = {
            billUser: document.getElementById('ue').innerText,
            billTable: tableNumber,
            billStatus: "Pending",
            requestType: "LoadTable"
        };
        const mhb = {
            method: 'POST',
            headers: { 'Content-Type': 'Application/json', 'Access-Control-Allow-Origin': '*' },
            body: JSON.stringify(loadTablePayload)
        };
        const billWithItemsResponse = await fetch("http://localhost:8080/dashboard/loadtable", mhb);
        if (!billWithItemsResponse.ok) {
            console.log(billWithItemsResponse.status + ": " + billWithItemsResponse.statusText);
        }
        else {
            const billWithItems = await billWithItemsResponse.json();
            // retrive table bill and items data and populate on screen	
            if (billWithItems.message === "Requested without authentication.") {
                // display custom dialog error
            }
            else {
                console.log(billWithItems);
                document.getElementById("dt").innerText = new Date().toISOString().substring(0, 10);
                document.getElementById("num").innerText = billWithItems.billNumber;
                document.getElementById("tab").innerText = billWithItems.billTable;
                document.getElementById("stat").innerText = billWithItems.tableStatus;
                document.getElementById("pmt").innerText = billWithItems.billStatus;
                document.getElementById("total").innerText = "₹ " + billWithItems.billTotal;
                const parentList = document.getElementById("itemlist");
                parentList.replaceChildren();
                console.log("b4 entering loop");
                for (let i = 0;i < billWithItems.itemList.length;i++) {
                    console.log("entered in loop");
                    const rowDiv = document.createElement("div");
                    rowDiv.className = "bill-row";
                    //rowDiv.id = "3";
                    const span1 = document.createElement("span");
                    span1.textContent = billWithItems.itemList[i].serial;
                    const span2 = document.createElement("span");
                    span2.textContent = billWithItems.itemList[i].quantity;
                    const span3 = document.createElement("span");
                    span3.textContent = billWithItems.itemList[i].description;
                    const span4 = document.createElement("span");
                    span4.style.textAlign = "right";
                    span4.textContent = billWithItems.itemList[i].amount;
                    rowDiv.appendChild(span1);
                    rowDiv.appendChild(span2);
                    rowDiv.appendChild(span3);
                    rowDiv.appendChild(span4);
                    parentList.appendChild(rowDiv);
                }
                document.getElementById("printButton").hidden = false;
                document.getElementById("clearButton").hidden = false;
                document.getElementById("completeButton").hidden = false;
                document.getElementById("itmcode").disabled = false;
                document.getElementById("itmlist").disabled = false;
                document.getElementById("qty").disabled = false;
                document.getElementById("srno").disabled = false;
                if (Number(billWithItems.billTotal) === 0)
                    noOrderYet = true;
                else
                    noOrderYet = false;
            }
        }
        document.getElementById("itmcode").focus();
    }
    catch (err) {
        console.log(err);
    }
}


function closeTable() {
    console.log("entered in close table method");
    document.getElementById("dt").innerText = "";
    document.getElementById("num").innerText = "";
    document.getElementById("tab").innerText = "";
    document.getElementById("stat").innerText = "";
    document.getElementById("pmt").innerText = "";
    document.getElementById("itmcode").value = "";
    document.getElementById("itmlist").value = "";
    document.getElementById("qty").value = "";
    document.getElementById("srno").value = "";
    console.log("b4 deleting");
    document.getElementById("itemlist").replaceChildren();
    console.log("after deleted");
    document.getElementById("total").innerText = "";
    document.getElementById("printButton").hidden = true;
    document.getElementById("clearButton").hidden = true;
    document.getElementById("completeButton").hidden = true;
    document.getElementById("t1").focus();
    document.getElementById("itmcode").disabled = true;
    document.getElementById("itmlist").disabled = true;
    document.getElementById("qty").disabled = true;
    document.getElementById("srno").disabled = true;

}

function addToCart() {

    const iitemName = document.getElementById('newItemName').value
    const iitemCode = document.getElementById('newItemCode').value
    const iitemCost = document.getElementById('newItemPrice').value

    if (iitemName === "" || iitemCode === "" || iitemCost === "") {
		alert("All fields are compulsory.");
		document.getElementById('newItemName').focus(); 
    }
    else {
        if (regItemsCart.length < 51) {
            regItemsCart.push({ itemCode: iitemCode, itemName: iitemName, itemCost: iitemCost });
			document.getElementById('atc').innerText = "Add To Cart (" + regItemsCart.length + ")";
			  document.getElementById('newItemName').value ="";
			 document.getElementById('newItemCode').value="";
			   document.getElementById('newItemPrice').value="";
			   document.getElementById('newItemName').focus();
			alert("Added to cart.");
        }
        else {
            document.getElementById('sendCartButton').focus();
			alert("Max 25 items at a time.");
        }
    }
}


async function sendCart(){
	
	if(regItemsCart.length === 0){
		alert("Min 1 item in cart needed before sending.");
		document.getElementById('newItemName').focus();
	}
	else{
	try{
		toggleModal();
		const regItemsPayload = {
		            billUser: document.getElementById('ue').innerText,
		            requestType: "LoadTable",
					items: regItemsCart
		        };
		const mhb = {
		           method: 'POST',
		           headers: { 'Content-Type': 'Application/json', 'Access-Control-Allow-Origin': '*' },
		           body: JSON.stringify(regItemsPayload)
		       };
			   console.log(JSON.stringify(regItemsPayload,null,2));
			   const regItemsResponse = await fetch("http://localhost:8080/dashboard/registeritems", mhb);
			           if (!regItemsResponse.ok) {
			               console.log(regItemsResponse.status + ": " + regItemsResponse.statusText);
						   //custom dialog box 
			           }
			           else {
			               const result = await regItemsResponse.text();
						   errNum = 10;
						   if(result==="saved")
							{
								showAlert("Information","Items Registered! Those which are duplicate or not unique will be automatically ignored.");
								regItemsCart=[];
							}
						   else
							showAlert("Information",result);
			           }
	}
	catch(error){
		console.log(error);
	}
	}
}

function toggleModal() {
    const modal = document.getElementById('registerModal');
    modal.style.display = (modal.style.display === 'flex') ? 'none' : 'flex';
	if(modal.style.display === 'flex') document.getElementById('newItemName').focus();
	else document.getElementById("t1").focus();
}

function saveItem() {
    const name = document.getElementById('newItemName').value;
    if (name) {
        alert(name + " added!");
        toggleModal();
    }
}


async function doLogout() {
    const mhb = {
        method: 'POST',
        headers: { 'Content-Type': 'text/plain', 'Access-Control-Allow-Origin': '*' },
        body: document.getElementById('ue').innerText
    };
    console.log(mhb);
    const logOutResponse = await fetch("http://localhost:8080/logout", mhb);
    if (!logOutResponse.ok) {
        console.log(logOutResponse.status + ": " + logOutResponse.statusText);
    }
    else {
        const logStatus = await logOutResponse.text();
        if (logStatus === "done") {
            window.location.href = "/login";
        }
        else {
            showAlert("Information", logStatus);
        }
    }

}


/* Alert Box Script */

function showAlert(title, message) {
    if (title) document.getElementById('alertTitle').innerText = title;
    if (message) document.getElementById('alertMessage').innerText = message;
    const overlay = document.getElementById('customAlertOverlay');
    overlay.classList.add('active');
    setTimeout(() => { document.getElementById("alertOkBtn").focus(); }, 50);
}

function closeAlert() {
    const overlay = document.getElementById('customAlertOverlay');
    overlay.classList.remove('active');
    switch (errNum) {
        case 1:
            document.getElementById("itmcode").focus();
            break;
        case 2:
            document.getElementById("srno").focus();
            break;
        case 3:
            document.getElementById("srno").focus();
            break;
        case 4:
            document.getElementById("itmcode").focus();
            break;
        case 5:
            document.getElementById("itmcode").focus();
            break;
        case 6:
            document.getElementById("t1").focus();
            break;
        case 7:
            document.getElementById("itmcode").focus();
            break;
        case 8:
            document.getElementById('newItemName').focus();
            break;
        case 9:
            document.getElementById('sendCartButton').focus();
            break;
		case 10:
			toggleModal();
			document.getElementById("t1").focus();
			break;	
    }
}


/* Yesno box script */

function showDialog(question) {
    if (question) document.getElementById('question').innerText = question;
    const overlay = document.getElementById('customDialogOverlay');
    overlay.classList.add('active');
    setTimeout(() => { document.getElementById('dialogYesBtn').focus(); }, 50);
}

function closeDialog() {
    const overlay = document.getElementById('customDialogOverlay');
    overlay.classList.remove('active');
}

function handleResponse(isYes) {
    closeDialog();
    if (isYes) {
        switch (actNum) {
            case 1:
                removeItemAndUpdate();
                break;
            case 2:
                clearItemsAndUpdate();
                break;
            case 3:
                loadPrintPage();
                break;
            case 4:
                completeBillAndUpdate();
                break;
        }
    }
}