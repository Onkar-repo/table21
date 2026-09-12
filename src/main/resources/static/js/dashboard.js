console.log("hello");
let errNum;
function loadUserName() {
    const querryString = window.location.search;
    const querryParams = new URLSearchParams(querryString);
    document.getElementById('ue').innerText = querryParams.get('userEmail');
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
					tot+=Number(itemList[i].amount);
                    rowDiv.appendChild(span1);
                    rowDiv.appendChild(span2);
                    rowDiv.appendChild(span3);
                    rowDiv.appendChild(span4);
                    parentList.appendChild(rowDiv);
                }
				document.getElementById("total").innerText = "₹ " + tot;
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
}

function toggleModal() {
    const modal = document.getElementById('registerModal');
    modal.style.display = (modal.style.display === 'flex') ? 'none' : 'flex';
}

function saveItem() {
    const name = document.getElementById('newItemName').value;
    if (name) {
        alert(name + " added!");
        toggleModal();
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
    }
}


/* Yesno box script */

function showDialog() {
    const overlay = document.getElementById('customDialogOverlay');
    overlay.classList.add('active');
    // Auto-focus primary action for accessibility
    document.getElementById('dialogYesBtn').focus();
}

function closeDialog() {
    const overlay = document.getElementById('customDialogOverlay');
    overlay.classList.remove('active');
}