console.log("hello");
function loadUserName() {
    const querryString = window.location.search;
    const querryParams = new URLSearchParams(querryString);
    document.getElementById('ue').innerText = querryParams.get('userEmail');
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
                dropdown.innerHTML = "<option selected>Select an item...</option>";

                for (let i = 0;i < listOfItems.length;i++) {
                    const temp = document.createElement("option");
                    temp.value = listOfItems[i].itemCode + ":" + listOfItems[i].itemName;
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
				document.getElementById("dt").innerText = new Date().toISOString().substring(0,10);
				document.getElementById("num").innerText = billWithItems.billNumber;
				document.getElementById("tab").innerText = billWithItems.billTable;
				document.getElementById("stat").innerText = billWithItems.tableStatus;
				document.getElementById("pmt").innerText = billWithItems.billStatus;
				document.getElementById("total").innerText = "₹ " + billWithItems.billTotal;
				const parentList = document.getElementById("itemlist");
				parentList.replaceChildren();
				
				for(let i=0;i<billWithItems.itemList.length;i++){
					const rowDiv = document.createElement("div");
					rowDiv.className = "bill-row";
					rowDiv.id = "3";		
					const span1 = document.createElement("span");
					span1.textContent = i+1;
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
    document.getElementById("itmlist").value = "Select an item...";
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