console.log("hello");
function loadUserName(){
	const querryString = window.location.search;
	const querryParams =  new URLSearchParams(querryString);
	document.getElementById('ue').innerText = querryParams.get('userEmail');
}



async function createEmptyBill(){
	
	try{
	const newEmptyBillRequest = {
		billUser: document.getElementById('ue').innerText,
		billTable: document.getElementById("t1").value,
		billStatus:	"Pending"
	};
const mhb = {
	method : 'POST',
	headers: { 'Content-Type': 'Application/json', 'Access-Control-Allow-Origin': '*' },
	body: JSON.stringify(newEmptyBillRequest)
};
newEmptyBillResponse = await fetch("http://localhost:8080/dashboard", mhb);
if(!newEmptyBillResponse.ok){
	console.log(newEmptyBillResponse.status + ": " + newEmptyBillResponse.statusText);
}
else{
	jsonResponse = await newEmptyBillResponse.JSON();
// retrive table bill and items data and populate on screen	
	
}


}
catch(err){
	console.log(err);
}
}


function closeTable(){
	console.log("entered in close table method");
	document.getElementById("dt").innerText="";
	document.getElementById("num").innerText="";
	document.getElementById("tab").innerText="";
	document.getElementById("stat").innerText="";
	document.getElementById("pmt").innerText="";
	document.getElementById("itmcode").value="";
	document.getElementById("itmlist").value="Select an item...";
	document.getElementById("qty").value="";
	document.getElementById("srno").value="";
	console.log("b4 deleting");
	document.getElementById("itemlist").replaceChildren();
	console.log("after deleted");
	document.getElementById("total").innerText="";
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
    if(name) {
        alert(name + " added!");
        toggleModal();
    }
}